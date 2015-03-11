package com.vib15.vibrations.app;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.Toast;

import com.vib15.vibrations.app.data.EventsDbHelper;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Agarwal on 12-01-2015.
 */
public class EventDetails extends ActionBarActivity implements ActionBar.TabListener {
    private Map<String,Integer> evt= new HashMap<String,Integer>();
    private ViewPager viewPager;
    private Menu menu;
    private TabsPagerAdapter mAdapter;
    public static EventsDbHelper databaseHelper;
    public static Cursor c;
    boolean pageselect;
    private static String SELECTED_KEY= "pos";
    private static int selectedPos= ListView.INVALID_POSITION;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.eventdetails);
        Intent i = getIntent();
        final ActionBar actionbar=getSupportActionBar();
        Toast.makeText(getApplicationContext(), i.getStringExtra("updated"), Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(), "Swipe Left or Right for other events!", Toast.LENGTH_LONG).show();
        actionbar.setDisplayShowTitleEnabled(true);
        databaseHelper=new EventsDbHelper(this);
        c=databaseHelper.getRegisterableRecords();
        c.moveToFirst();
        actionbar.setTitle(c.getString(3));
        String t = c.getString(3);
        int x=0;
        evt.put(t,x);
      while(c.moveToNext()){
            x++;
            if(!t.equalsIgnoreCase(c.getString(3))) {
                t=c.getString(3);
                evt.put(t,x);
            }
        }
        c.moveToFirst();
// Initialization
        viewPager = (ViewPager) findViewById(R.id.pager);
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
                public void onPageScrolled(int i, float v, int i2) {
            }

            @Override
            public void onPageSelected(int position) {
                pageselect = true;
                c.moveToPosition(position);
                actionbar.setTitle(c.getString(3));
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_KEY)) {
            // The listview probably hasn't even been populated yet.  Actually perform the
            // swapout in onLoadFinished.
            selectedPos = savedInstanceState.getInt(SELECTED_KEY);
        }

    }
    public void onSaveInstanceState(Bundle outState) {
        // When tablets rotate, the currently selected list item needs to be saved.
        // When no item is selected, mPosition will be set to Listview.INVALID_POSITION,
        // so check for that before storing.
        if (selectedPos != ListView.INVALID_POSITION) {
            outState.putInt(SELECTED_KEY, selectedPos);
            viewPager.setCurrentItem(selectedPos);
        }
        super.onSaveInstanceState(outState);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu m) {
        // Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
        menu=m;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.ed, menu);

        return super.onCreateOptionsMenu(menu);


        //return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.disp:
                Toast.makeText(getBaseContext(),"Use menu to change Category!...",Toast.LENGTH_LONG).show();
                return true;
            case R.id.abhivyakti:
                viewPager.setCurrentItem(evt.get("Abhivyakti"));
                return true;
            case R.id.creative:
                viewPager.setCurrentItem(evt.get("Creative Events"));
                return true;
            case R.id.lekhani:
                viewPager.setCurrentItem(evt.get("Lekhani"));
                return true;
            case R.id.personality:
                viewPager.setCurrentItem(evt.get("Personality"));
                return true;
            case R.id.quests:
                viewPager.setCurrentItem(evt.get("Quests"));
                return true;
            case R.id.taal:
                viewPager.setCurrentItem(evt.get("Taal"));
                return true;
            case R.id.talentwalk:
                viewPager.setCurrentItem(evt.get("Talent Walk"));
                return true;
            case R.id.tarang:
                viewPager.setCurrentItem(evt.get("Tarang"));
                return true;
            case R.id.troogle:
                viewPager.setCurrentItem(evt.get("Troogle"));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public class TabsPagerAdapter extends FragmentStatePagerAdapter {

        public TabsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int index) {
            selectedPos=index;
            Cursor c1=databaseHelper.getRegisterableRecords();
            int date = databaseHelper.getDay();
            c1.moveToPosition(index);
            int detail=c1.getInt(2);
            String time=c1.getString(6);
            String venue=c1.getString(4);
            int ImageID=c1.getInt(8);
            int day=c1.getInt(5);
            int hour=c1.getInt(6)/100;
            int min=c1.getInt(6)%100;
            int id=c1.getInt(0);
            int alarmstate=c1.getInt(9);
            String title=trimTitle(c1.getString(1));
            Bundle bundle=new Bundle();
            bundle.putInt("Date",date);
            bundle.putInt("Detail", detail);
            bundle.putString("Time", time);
            bundle.putString("Venue", venue);
            bundle.putInt("Image", ImageID);
            bundle.putInt("day", day);
            bundle.putInt("hour", hour);
            bundle.putInt("min", min);
            bundle.putInt("id", id);
            bundle.putInt("alarm", alarmstate);
            bundle.putString("title", title);
            DetailFragment fragment=new DetailFragment();
            fragment.setArguments(bundle);

            Log.v("Alarm state", "Index: " + index + "\n" + alarmstate + "" + (alarmstate == 0));
            return fragment;
        }

        @Override
        public int getCount() {

            return c.getCount();
        }
        @Override
        public CharSequence getPageTitle(int index) {
            c.moveToPosition(index);
            return trimTitle(c.getString(1));
        }

        private String trimTitle(String title)
        {
            int i=0;
            int l=title.length();

            while(i<l)
            {
                if(title.charAt(i)==40)
                {
                    title=title.substring(0, i);
                    break;
                }
                i++;

            }
            title=title.trim();
            return(title);
        }

    }
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        databaseHelper.close();
        super.onDestroy();
    }
    @Override
    public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
        // TODO Auto-generated method stub

    }
    @Override
    public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
        // TODO Auto-generated method stub
        if (pageselect) ;
        else
        {
            c.moveToFirst();
            int pos = 0;
            while (!c.getString(3).equals(arg0.getText())) {
                pos++;
                c.moveToNext();
            }
            viewPager.setCurrentItem(pos, true);
        }

        pageselect=false;
    }
    @Override
    public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
        // TODO Auto-generated method stub

    }

    public void setAlarm(int id, int val)
    {
        databaseHelper.setAlarm(id, val);
    }
}
