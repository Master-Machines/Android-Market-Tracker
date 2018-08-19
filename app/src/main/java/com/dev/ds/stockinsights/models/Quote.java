package com.dev.ds.stockinsights.models;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

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


    public List<QuoteInfoItem> getInfoItems() {
        List<QuoteInfoItem> items = new ArrayList<QuoteInfoItem>();

        items.add(new QuoteInfoItem("Open", getFormattedPriceString(open)));
        items.add(new QuoteInfoItem("Close", getFormattedPriceString(close)));
        items.add(new QuoteInfoItem("Prev Close", getFormattedPriceString(previousClose)));
        items.add(new QuoteInfoItem("52 Week Low", getFormattedPriceString(week52Low)));
        items.add(new QuoteInfoItem("52 Week High", getFormattedPriceString(week52High)));
        items.add(new QuoteInfoItem("ytd change", getFormattedPercentString(ytdChange)));
        items.add(new QuoteInfoItem("Market Cap", "$" + Long.toString(marketCap)));
        items.add(new QuoteInfoItem("Sector", sector));
        items.add(new QuoteInfoItem("Exchange", primaryExchange));
        items.add(new QuoteInfoItem("Extended Price", getFormattedPriceString(extendedPrice)));

        return items;
    }

    public static String getFormattedPriceString(double price) {
        if (price < 0) {
            return String.format("-$%.2f", -price);
        }
        return String.format("$%.2f", price);
    }

    public static String getFormattedPercentString(double percent) {
        percent *= 100;
        if (percent > 0) {
            return String.format("+%.2f%%", percent);
        }
        return String.format("%.2f%%", percent);
    }

    public class QuoteInfoItem {
        public String title;
        public String val;

        public QuoteInfoItem(String title, String val) {
            this.title = title;
            this.val = val;
        }
    }

}
