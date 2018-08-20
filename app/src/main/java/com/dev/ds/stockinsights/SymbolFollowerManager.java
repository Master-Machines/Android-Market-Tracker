package com.dev.ds.stockinsights;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashSet;
import java.util.prefs.Preferences;

public class SymbolFollowerManager {
    private static final String FOLLOWED_SYMBOLS_KEY = "FOLLOWED_SYMBOLS_KEY";
    private static SymbolFollowerManager instance;


    public static SymbolFollowerManager getInstance() {
        if (instance == null) {
            instance = new SymbolFollowerManager();
        }
        return instance;
    }

    public HashSet<String> getFollowedSymbols(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        HashSet<String> followedSymbols = (HashSet<String>) sharedPreferences.getStringSet(FOLLOWED_SYMBOLS_KEY, new HashSet<String>());
        return followedSymbols;
    }

    public void followSymbol(String symbol, Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        HashSet<String> followedSymbols = (HashSet<String>) sharedPreferences.getStringSet(FOLLOWED_SYMBOLS_KEY, new HashSet<String>());
        followedSymbols.add(symbol);
        saveStringSet(followedSymbols, sharedPreferences);
    }

    public void unfollowSymbol(String symbol, Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        HashSet<String> followedSymbols = (HashSet<String>) sharedPreferences.getStringSet(FOLLOWED_SYMBOLS_KEY, null);
        followedSymbols.remove(symbol);
        saveStringSet(followedSymbols, sharedPreferences);
    }

    public boolean isSymbolFollowed(String symbol, Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        HashSet<String> followedSymbols = (HashSet<String>) sharedPreferences.getStringSet(FOLLOWED_SYMBOLS_KEY, new HashSet<String>());
        return followedSymbols.contains(symbol);
    }

    private void saveStringSet(HashSet<String> hashSet, SharedPreferences sharedPreferences) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putStringSet(FOLLOWED_SYMBOLS_KEY, hashSet);
        editor.commit();
    }



}
