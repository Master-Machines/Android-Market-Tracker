package com.dev.ds.stockinsights;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.ds.stockinsights.adapters.StockDetailAdapter;
import com.dev.ds.stockinsights.models.Quote;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StockDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StockDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StockDetailsFragment extends Fragment {
    private static final String ARG_STOCK_SYMBOL = "ARG_STOCK_SYMBOL";

    private StockViewModel stockViewModel;
    private StockDetailAdapter detailAdapter;
    private String stockSymbol;
    private RecyclerView recyclerView;

//    private OnFragmentInteractionListener mListener;

    public StockDetailsFragment() {
        // Required empty public constructor
    }

    public static StockDetailsFragment newInstance(String symbol) {
        StockDetailsFragment fragment = new StockDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_STOCK_SYMBOL, symbol);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            stockSymbol = getArguments().getString(ARG_STOCK_SYMBOL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_stock_details, container, false);

        recyclerView = (RecyclerView)rootView.findViewById(R.id.stock_details_list_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(this.detailAdapter);

        // Inflate the layout for this fragment
        return rootView;
    }

    public void setViewModel(StockViewModel viewModel) {
        this.stockViewModel = viewModel;
        this.detailAdapter = new StockDetailAdapter(getContext());
        this.detailAdapter.setDataset(viewModel.activeQuote.getInfoItems());
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }


//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Quote quote)
//    }
}
