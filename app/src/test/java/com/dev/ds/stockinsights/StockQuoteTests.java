package com.dev.ds.stockinsights;


import com.dev.ds.stockinsights.models.Quote;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;

public class StockQuoteTests {

    @Test
    public void quote_formatted_percent_string_test() {
        assertEquals(Quote.getFormattedPercentString(0.00100), "+0.10%");

        assertEquals(Quote.getFormattedPercentString(0.00111), "+0.11%");

        assertEquals(Quote.getFormattedPercentString(0.0550), "+5.50%");

        assertEquals(Quote.getFormattedPercentString(0.1), "+10.00%");

        assertEquals(Quote.getFormattedPercentString(-0.1), "-10.00%");

        assertEquals(Quote.getFormattedPercentString(-0.065321234), "-6.53%");


        assertEquals(Quote.getFormattedPercentString(0.0), "0.00%");
    }

    @Test
    public void quote_formatted_price_string_test() {
        assertEquals(Quote.getFormattedPriceString(10), "$10.00");


        assertEquals(Quote.getFormattedPriceString(10.001), "$10.00");


        assertEquals(Quote.getFormattedPriceString(1.555), "$1.56");

        assertEquals(Quote.getFormattedPriceString(155.32), "$155.32");


        assertEquals(Quote.getFormattedPriceString(-3155.3233333), "-$3155.32");
    }

}
