package com.vib15.vibrations.app;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class Sponsor extends ActionBarActivity implements SponsorFragment.Callback{
    SponsorFragment sf;
    private static String SELECTED_TAG="tab";
    private static String name = "";
    TextView iv;
    @Override
    public void onItemSelected(String name) {
        this.name=name;
        iv.setText(name);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(SELECTED_TAG, name);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsor);
        iv= (TextView) findViewById(R.id.sponsorHead);
        sf = new SponsorFragment();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, sf)
                    .commit();

        }
        if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_TAG)) {
            name=savedInstanceState.getString(SELECTED_TAG);
        }
        iv.setText(name);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sponsor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();



        return super.onOptionsItemSelected(item);
    }

}
