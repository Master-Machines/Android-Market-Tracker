package com.dev.ds.stockinsights;

import com.dev.ds.stockinsights.models.Quote;
import com.dev.ds.stockinsights.models.QuoteInfo;
import com.dev.ds.stockinsights.models.Symbol;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface StockApi {

    @GET("stock/{symbol}/batch?types=quote,news,chart")
    Observable<QuoteInfo> getQuote(@Path("symbol") String symbol);

    @GET("ref-data/symbols")
    Observable<Symbol[]> getAllSymbols();

}
