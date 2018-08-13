package com.dev.ds.stockinsights.models;

import com.google.gson.annotations.Expose;

public class Symbol {

    @Expose
    public String symbol;

    @Expose
    public String name;


    private String upperCaseName;

    // Search string should be upper case!
    public boolean containsSearch(String search) {
        if (upperCaseName == null) upperCaseName = name.toUpperCase();
        return symbol.indexOf(search) > -1 || upperCaseName.indexOf(search) > -1;
    }
}
