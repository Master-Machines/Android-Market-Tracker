package com.dev.ds.stockinsights.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dev.ds.stockinsights.R;
import com.dev.ds.stockinsights.models.Logo;
import com.dev.ds.stockinsights.models.Quote;

import java.util.ArrayList;
import java.util.List;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class StockDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_DETAIL = 1;
    private List<Quote.QuoteInfoItem> dataset;
    private Quote currentQuote;
    private Fragment parentFragment;
    private HeaderViewHolder headerViewHolder;
    public StockListAdapter.StockSelectionInterface stockSelectionDelegate;
    public PublishSubject<Logo> imageUrlObservable;

    public StockDetailAdapter(Fragment parentFragment) {
        this.dataset = new ArrayList<Quote.QuoteInfoItem>();
        this.parentFragment = parentFragment;

        imageUrlObservable = PublishSubject.create();

        Disposable disposable = imageUrlObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new  DisposableObserver<Logo>() {

                    @Override
                    public void onNext(Logo logo) {
                        Glide.with(parentFragment).load(logo.url).into(headerViewHolder.imageView);
                    }
                    @Override
                    public void onError(Throwable e) { }
                    @Override
                    public void onComplete() { }
                });
    }


    public static class DetailViewHolder extends RecyclerView.ViewHolder {
        public View v;
        public TextView titleTextView;
        public TextView valueTextView;

        public DetailViewHolder(View v) {
            super(v);
            this.v = v;
            titleTextView = v.findViewById(R.id.detail_title);
            valueTextView = v.findViewById(R.id.detail_value);
        }
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public View v;
        public TextView companyTextView;
        public TextView tickerTextView;
        public TextView priceTextView;
        public TextView percentChangeTextView;
        public ImageView imageView;

        public HeaderViewHolder(View v) {
            super(v);
            this.v = v;
            tickerTextView = v.findViewById(R.id.ticker_name);
            companyTextView = v.findViewById(R.id.stock_name);
            priceTextView = v.findViewById(R.id.stock_price);
            percentChangeTextView = v.findViewById(R.id.percent_change);
            imageView = v.findViewById(R.id.logo_view);
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {

        if (viewType == VIEW_TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.stock_details_header, parent, false);

            headerViewHolder = new StockDetailAdapter.HeaderViewHolder(v);
            return (RecyclerView.ViewHolder) headerViewHolder;
        } else {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.stock_details_cell, parent, false);

            StockDetailAdapter.DetailViewHolder vh = new StockDetailAdapter.DetailViewHolder(v);
            return (RecyclerView.ViewHolder) vh;
        }
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_HEADER:
                HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
                headerHolder.companyTextView.setText(currentQuote.companyName);
                headerHolder.tickerTextView.setText(currentQuote.symbol);
                headerHolder.priceTextView.setText(Double.toString(currentQuote.latestPrice));

                Double changePercent = currentQuote.changePercent * 100;

                if (currentQuote.changePercent > 0) {
                    headerHolder.percentChangeTextView.setText(String.format("+%.2f%%", changePercent));
                    headerHolder.percentChangeTextView.setTextColor(ContextCompat.getColor(parentFragment.getContext(), R.color.greenStock));
                } else if (currentQuote.changePercent < 0){
                    headerHolder.percentChangeTextView.setText(String.format("%.2f%%", changePercent));
                    headerHolder.percentChangeTextView.setTextColor(ContextCompat.getColor(parentFragment.getContext(), R.color.redStock));
                } else {
                    headerHolder.percentChangeTextView.setText("--- ");
                }
                break;
            case VIEW_TYPE_DETAIL:
                position -= 1;
                DetailViewHolder deatilHolder = (DetailViewHolder) holder;
                Quote.QuoteInfoItem infoItem = dataset.get(position);
                deatilHolder.titleTextView.setText(infoItem.title);
                deatilHolder.valueTextView.setText(infoItem.val);
                break;
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_HEADER;
        }
        return VIEW_TYPE_DETAIL;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return dataset.size() + 1;
    }

    public void setStockDetails(Quote quote) {
        this.dataset = quote.getInfoItems();
        this.currentQuote = quote;
        this.notifyDataSetChanged();
    }
}
