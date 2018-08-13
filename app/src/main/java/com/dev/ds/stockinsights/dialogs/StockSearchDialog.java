package com.dev.ds.stockinsights.dialogs;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.ds.stockinsights.R;
import com.dev.ds.stockinsights.StockViewModel;
import com.dev.ds.stockinsights.adapters.StockSearchAdapter;

public class StockSearchDialog extends DialogFragment {

    private StockViewModel stockViewModel;
    public StockSearchAdapter searchAdapter;
    private RecyclerView recyclerView;
    private TextInputEditText textInputEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_stock_search, container, false);

        recyclerView = v.findViewById(R.id.search_results_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(searchAdapter);
        textInputEditText = v.findViewById(R.id.search_input);

        textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchTerm = s.toString().toUpperCase();
                searchAdapter.filterSearchResults(searchTerm);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        searchAdapter.filterSearchResults("");
        return v;
    }

    public void setViewModel(StockViewModel stockViewModel) {
        this.stockViewModel = stockViewModel;
        this.searchAdapter = new StockSearchAdapter(this, this.stockViewModel.allSymbols);
    }

}
