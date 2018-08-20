package com.dev.ds.stockinsights;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.transition.Fade;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.dev.ds.stockinsights.adapters.StockListAdapter;
import com.dev.ds.stockinsights.adapters.StockSearchAdapter;
import com.dev.ds.stockinsights.dialogs.StockSearchDialog;
import com.dev.ds.stockinsights.models.QuoteInfo;

import java.util.HashSet;

import io.reactivex.observers.DisposableObserver;

public class HomeActivity extends AppCompatActivity implements StockListAdapter.StockSelectionInterface, StockSearchAdapter.StockSelectionInterface {
    private static final long FADE_DEFAULT_TIME = 400;

    private StockSearchDialog searchFragment;
    public StockViewModel stockViewModel;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        stockViewModel = new StockViewModel(this);
        stockViewModel.adapter.stockSelectionDelegate = this;

        stockViewModel.retrieveAllSymbols();

//        HomeActivityFragment homeFragment = (HomeActivityFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_home);
//        homeFragment.setViewModel(stockViewModel);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.colorLightText));
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSearchDialog();
            }
        });

        startHomeFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private void startHomeFragment() {
        HomeActivityFragment homeFragment = new HomeActivityFragment();
        homeFragment.setViewModel(this.stockViewModel);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        fragmentTransaction.replace(R.id.home_fragment_container, homeFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


        Fade exitFade = new Fade();
        exitFade.setDuration(FADE_DEFAULT_TIME);
        homeFragment.setExitTransition(exitFade);
    }

    private void showSearchDialog() {
        searchFragment = new StockSearchDialog();
        searchFragment.setViewModel(stockViewModel);
        searchFragment.searchAdapter.stockSearchSelectionDelegate = this;
        searchFragment.setShowsDialog(true);
        searchFragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        searchFragment.show(getSupportFragmentManager(), "dialog");

        Fade exitFade = new Fade();
        exitFade.setDuration(FADE_DEFAULT_TIME);
        searchFragment.setExitTransition(exitFade);
    }

    @Override
    public void stockSelected(String symbol) {
        this.stockViewModel.setActiveQuote(symbol);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        StockDetailsFragment detailsFragment = StockDetailsFragment.newInstance(symbol);
        detailsFragment.setViewModel(this.stockViewModel);
        fragmentTransaction.replace(R.id.home_fragment_container, detailsFragment);
        fragmentTransaction.addToBackStack(null);



        Fade enterFade = new Fade();
        enterFade.setDuration(FADE_DEFAULT_TIME);
        detailsFragment.setEnterTransition(enterFade);


        fragmentTransaction.commit();
    }


    @Override
    public void stockSelectedFromSearch(String symbol) {
        searchFragment.ShowLoadingAnimation();
        stockViewModel.getStockQuote(symbol, new Runnable() {
            @Override
            public void run() {
                getSupportFragmentManager().beginTransaction().remove(searchFragment).commit();
                stockSelected(symbol);
            }
        });
    }
}
