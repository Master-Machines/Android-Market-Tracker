package com.dev.ds.stockinsights;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A placeholder fragment containing a simple view.
 */
public class HomeActivityFragment extends Fragment {

    public StockViewModel stockViewModel;

    public HomeActivityFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        stockViewModel = new StockViewModel(getContext());
        stockViewModel.getStockQuote("TSLA");
        stockViewModel.getStockQuote("SNAP");
        stockViewModel.getStockQuote("VTI");
        stockViewModel.getStockQuote("TTWO");
        stockViewModel.getStockQuote("AAPL");

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView recyclerView = (RecyclerView)rootView.findViewById(R.id.stock_list_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(stockViewModel.adapter);


        return rootView;
    }
}
