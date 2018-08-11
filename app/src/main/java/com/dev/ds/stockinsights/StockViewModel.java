package com.dev.ds.stockinsights;

import android.content.Context;

import com.dev.ds.stockinsights.adapters.StockListAdapter;
import com.dev.ds.stockinsights.models.Quote;
import com.dev.ds.stockinsights.models.QuoteInfo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class StockViewModel {

    public List<Quote> quoteList;
    public StockListAdapter adapter;

    public StockViewModel(Context context) {
        quoteList = new ArrayList<Quote>();
        adapter = new StockListAdapter(context);
    }

    public void getStockQuote(String symbol) {
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
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });
    }

}
