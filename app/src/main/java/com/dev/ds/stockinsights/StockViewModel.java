package com.dev.ds.stockinsights;

import android.content.Context;
import android.util.Log;

import com.dev.ds.stockinsights.adapters.StockListAdapter;
import com.dev.ds.stockinsights.models.Logo;
import com.dev.ds.stockinsights.models.Quote;
import com.dev.ds.stockinsights.models.QuoteInfo;
import com.dev.ds.stockinsights.models.Symbol;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class StockViewModel {

    public Quote activeQuote;
    public List<Quote> quoteList;
    public List<Logo> logoList;
    public StockListAdapter adapter;
    public Symbol[] allSymbols;

    public StockViewModel(Context context) {
        quoteList = new ArrayList<Quote>();
        logoList = new ArrayList<Logo>();
        adapter = new StockListAdapter(context);
    }

    public void getStockQuote(String symbol, Runnable completed) {
        Observable<QuoteInfo> quoteObservable = StockService.getInstance().getStockApi().getQuote(symbol);
        Disposable disposable = quoteObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new  DisposableObserver<QuoteInfo>() {

            @Override
            public void onNext(QuoteInfo info) {
                quoteList.add(info.quote);
                adapter.addQuote(info.quote);
            }

            @Override
            public void onError(Throwable e) { }
            @Override
            public void onComplete() {
                if (completed != null) {
                    completed.run();
                }
            }
        });
    }

    public void retrieveAllSymbols() {
        Observable<Symbol[]> symbolsObservable = StockService.getInstance().getStockApi().getAllSymbols();
        Disposable disposable = symbolsObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new  DisposableObserver<Symbol[]>() {

                    @Override
                    public void onNext(Symbol[] symbols) {
                        allSymbols = symbols;
                    }
                    @Override
                    public void onError(Throwable e) { }
                    @Override
                    public void onComplete() { }
                });
    }

    public void setActiveQuote(String symbol) {
        for(Quote quote: this.quoteList) {
            if (quote.symbol.equals(symbol)) {
                this.activeQuote = quote;
            }
        }
    }

    public Logo getCachedLogo(String symbol) {
        for (Logo logo: logoList) {
            if (logo.symbol.equals(symbol)) {
                return logo;
            }
        }
        return null;
    }

    public Observable<Logo> retrieveLogo(String symbol) {
        Observable<Logo> logoObservable = StockService.getInstance().getStockApi().getLogo(symbol);

        Disposable disposable = logoObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new  DisposableObserver<Logo>() {

                    @Override
                    public void onNext(Logo logo) {
                        logo.symbol = symbol;
                        logoList.add(logo);
                    }
                    @Override
                    public void onError(Throwable e) {
                    }
                    @Override
                    public void onComplete() { }
                });
        return logoObservable;
    }



}
