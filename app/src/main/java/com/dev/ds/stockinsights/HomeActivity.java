package com.dev.ds.stockinsights;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.transition.Fade;
import android.support.transition.TransitionInflater;
import android.support.transition.TransitionSet;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.dev.ds.stockinsights.adapters.StockListAdapter;

public class HomeActivity extends AppCompatActivity implements StockListAdapter.StockSelectionInterface {
    private static final long FADE_DEFAULT_TIME = 250;

    public StockViewModel stockViewModel;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        stockViewModel = new StockViewModel(this);
        stockViewModel.adapter.stockSelectionDelegate = this;
        stockViewModel.getStockQuote("TSLA");
        stockViewModel.getStockQuote("SNAP");
        stockViewModel.getStockQuote("VTI");
        stockViewModel.getStockQuote("TTWO");
        stockViewModel.getStockQuote("AAPL");

//        HomeActivityFragment homeFragment = (HomeActivityFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_home);
//        homeFragment.setViewModel(stockViewModel);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.colorLightText));
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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

}
