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

public class StockListAdapter extends RecyclerView.Adapter<StockListAdapter.ViewHolder> {
    private List<Quote> dataset;
    private Context context;

    public StockListAdapter(Context context) {
        this.dataset = new ArrayList<Quote>();
        this.context = context;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView companyTextView;
        public TextView tickerTextView;
        public TextView priceTextView;
        public TextView percentChangeTextView;

        public ViewHolder(View v) {
            super(v);
            tickerTextView = v.findViewById(R.id.ticker_name);
            companyTextView = v.findViewById(R.id.stock_name);
            priceTextView = v.findViewById(R.id.stock_price);
            percentChangeTextView = v.findViewById(R.id.percent_change);
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public StockListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stock_ticker_cell, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Quote quote = dataset.get(position);
        holder.companyTextView.setText(quote.companyName);
        holder.tickerTextView.setText(quote.symbol);
        holder.priceTextView.setText(Double.toString(quote.latestPrice));

        Double changePercent = quote.changePercent * 100;

        if (quote.changePercent > 0) {
            holder.percentChangeTextView.setText(String.format("+%.2f%%", changePercent));
            holder.percentChangeTextView.setTextColor(ContextCompat.getColor(context, R.color.greenStock));
        } else if (quote.changePercent < 0){
            holder.percentChangeTextView.setText(String.format("%.2f%%", changePercent));
            holder.percentChangeTextView.setTextColor(ContextCompat.getColor(context, R.color.redStock));
        } else {
            holder.percentChangeTextView.setText("--- ");
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void setDataset(List<Quote> quotes) {
        this.dataset = quotes;
        this.notifyDataSetChanged();
    }

    public void addQuote(Quote quote) {
        this.dataset.add(quote);
        this.notifyDataSetChanged();
    }
}
