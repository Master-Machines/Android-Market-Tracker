package com.dev.ds.stockinsights.models;

import com.google.gson.annotations.Expose;

public class Quote {

    @Expose
    public String symbol;
    @Expose
    public String companyName;
    @Expose
    public String primaryExchange;
    @Expose
    public String sector;
    @Expose
    public String calculationPrice;
    @Expose
    public double open;
    @Expose
    public double openTime;
    @Expose
    public double close;
    @Expose
    public long closeTime;
    @Expose
    public double high;
    @Expose
    public double low;
    @Expose
    public double latestPrice;
    @Expose
    public String latestSource;
    @Expose
    public String latestTime;
    @Expose
    public long latestUpdate;
    @Expose
    public long latestVolume;
    @Expose
    public double iexRealtimePrice;
    @Expose
    public long iexRealtimeSize;
    @Expose
    public long iexLastUpdated;
    @Expose
    public double delayedPrice;
    @Expose
    public long delayedPriceTime;
    @Expose
    public double extendedPrice;
    @Expose
    public double extendedChange;
    @Expose
    public double extendedChangePercent;
    @Expose
    public long extendedPriceTime;
    @Expose
    public double previousClose;
    @Expose
    public double change;
    @Expose
    public double changePercent;
    @Expose
    public double iexMarketPercent;
    @Expose
    public long iexVolume;
    @Expose
    public long avgTotalVolume;
    @Expose
    public long marketCap;
    @Expose
    public double peRatio;
    @Expose
    public double week52High;
    @Expose
    public double week52Low;
    @Expose
    public double ytdChange;

}
