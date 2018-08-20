package com.dev.ds.stockinsights;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.ds.stockinsights.adapters.StockListAdapter;

import java.util.HashSet;

/**
 * A placeholder fragment containing a simple view.
 */
public class HomeActivityFragment extends Fragment {

    private StockViewModel stockViewModel;
    private RecyclerView recyclerView;

    public HomeActivityFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = (RecyclerView)rootView.findViewById(R.id.stock_list_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if (stockViewModel != null) {
            recyclerView.setAdapter(stockViewModel.adapter);
        }


        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getFollowedSymbols();
    }

    public void setViewModel(StockViewModel viewModel) {
        this.stockViewModel = viewModel;
        if(recyclerView != null) {
            recyclerView.setAdapter(stockViewModel.adapter);
        }
    }

    private void getFollowedSymbols() {
        HashSet<String> followedSymbols = SymbolFollowerManager.getInstance().getFollowedSymbols(getContext());
        for (String symbol: followedSymbols) {
            this.stockViewModel.getStockQuote(symbol, null);
        }
    }
}
