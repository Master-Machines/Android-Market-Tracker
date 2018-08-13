package com.dev.ds.stockinsights.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dev.ds.stockinsights.R;
import com.dev.ds.stockinsights.models.Symbol;

import java.util.ArrayList;
import java.util.List;

public class StockSearchAdapter extends RecyclerView.Adapter<StockSearchAdapter.ViewHolder> {

    private static final int MAX_SEARCH_RESULTS = 300;

    public interface StockSelectionInterface {
        void stockSelectedFromSearch(String symbol);
    }


    private Symbol[] allSymbols;
    private List<Symbol> symbolsMatchingSearch;
    private String currentSearch = "";
    private Fragment parentFragment;
    public StockSearchAdapter.StockSelectionInterface stockSearchSelectionDelegate;

    public StockSearchAdapter(Fragment fragment, Symbol[] allSymbols) {
        this.symbolsMatchingSearch = new ArrayList<Symbol>();
        this.allSymbols = allSymbols;
        this.parentFragment = fragment;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View v;
        public TextView symbolTextView;
        public TextView nameTextView;

        public ViewHolder(View v) {
            super(v);
            this.v = v;
            symbolTextView = v.findViewById(R.id.symbol_text);
            nameTextView = v.findViewById(R.id.stock_name_text);
        }

        public void setupClickListener(final Symbol symbol, final StockSearchAdapter.StockSelectionInterface stockSelectionDelegate) {
            v.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    stockSelectionDelegate.stockSelectedFromSearch(symbol.symbol);
                }
            });
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public StockSearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stock_search_cell, parent, false);

        StockSearchAdapter.ViewHolder vh = new StockSearchAdapter.ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(StockSearchAdapter.ViewHolder holder, int position) {
        Symbol symbol = symbolsMatchingSearch.get(position);
        holder.nameTextView.setText(symbol.name);
        holder.symbolTextView.setText(symbol.symbol);

        holder.setupClickListener(symbol, stockSearchSelectionDelegate);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return symbolsMatchingSearch.size();
    }

    public void filterSearchResults(final String newSearchTerm) {
//        if (currentSearch.length() < newSearchTerm.length()) {
//            // We don't need to iterate through the entire list again, we can just use the results from the last filter.
//            symbolsMatchingSearch.removeIf(s -> !s.containsSearch(newSearchTerm));
//        } else {
            symbolsMatchingSearch.clear();
            int counter = 0;
            while (symbolsMatchingSearch.size() < MAX_SEARCH_RESULTS && counter < allSymbols.length) {
                if(allSymbols[counter].containsSearch(newSearchTerm)) {
                    symbolsMatchingSearch.add(allSymbols[counter]);
                }
                counter ++;
            }
//        }

        currentSearch = newSearchTerm;
        this.notifyDataSetChanged();

    }


}
