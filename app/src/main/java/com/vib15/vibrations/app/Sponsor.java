package com.vib15.vibrations.app;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;


public class Sponsor extends ActionBarActivity implements SponsorFragment.Callback{
    SponsorFragment sf;
    ImageView iv;
    static int resID=R.drawable.sponsors1;
    private static String SELECTED_IMAGE="img";
    @Override
    public void onItemSelected(int resId,String name) {
            resID=resId;
            iv.setImageResource(resId);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(SELECTED_IMAGE, resID);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsor);
        iv= (ImageView) findViewById(R.id.sponsorImg);
        sf = new SponsorFragment();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, sf)
                    .commit();
        }
        if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_IMAGE)) {
            iv.setImageResource(savedInstanceState.getInt(SELECTED_IMAGE,resID));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sponsor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
