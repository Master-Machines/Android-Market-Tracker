package com.dev.ds.stockinsights.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dev.ds.stockinsights.R;
import com.dev.ds.stockinsights.models.Quote;

import java.util.ArrayList;
import java.util.List;

public class StockDetailAdapter extends RecyclerView.Adapter<StockDetailAdapter.ViewHolder>{
    private List<Quote.QuoteInfoItem> dataset;
    private Context context;
    public StockListAdapter.StockSelectionInterface stockSelectionDelegate;

    public StockDetailAdapter(Context context) {
        this.dataset = new ArrayList<Quote.QuoteInfoItem>();
        this.context = context;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View v;
        public TextView titleTextView;
        public TextView valueTextView;

        public ViewHolder(View v) {
            super(v);
            this.v = v;
            titleTextView = v.findViewById(R.id.detail_title);
            valueTextView = v.findViewById(R.id.detail_value);
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public StockDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stock_details_cell, parent, false);

        StockDetailAdapter.ViewHolder vh = new StockDetailAdapter.ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(StockDetailAdapter.ViewHolder holder, int position) {
        Quote.QuoteInfoItem infoItem = dataset.get(position);
        holder.titleTextView.setText(infoItem.title);
        holder.valueTextView.setText(infoItem.val);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void setDataset(List<Quote.QuoteInfoItem> quotes) {
        this.dataset = quotes;
        this.notifyDataSetChanged();
    }
}
