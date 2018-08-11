package com.dev.ds.stockinsights;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StockService {
    private static StockService instance;
    private static String BASE_URL = "https://api.iextrading.com/1.0/";
    private Retrofit retrofitInstance;
    private StockApi stockApi;

    public StockService() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        retrofitInstance = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        stockApi = retrofitInstance.create(StockApi.class);
    }

    public StockApi getStockApi() {
        return stockApi;
    }

    public static StockService getInstance() {
        if (instance == null) {
            instance = new StockService();
        }
        return instance;
    }




}
