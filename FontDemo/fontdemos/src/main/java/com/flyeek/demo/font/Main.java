package com.flyeek.demo.font;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.flyeek.demo.font.metrics.MetricsFragment;
import com.flyeek.demo.font.typeface.TypefaceFragment;


public class Main extends Activity {
    private static final int DEMO_INTRODUCTION = 0;
    private static final int DEMO_FONT_METRICS = 1;
    private static final int DEMO_FONT_TYPEFACE = 2;
    private static final int DEMO_SPANNABLE_STRING = 3;

    private String[] mDemoItemTitles;
    private CharSequence mCurrentDemoTitle;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDemoItemTitles = this.getResources().getStringArray(R.array.demoitems_array);
        mCurrentDemoTitle = getTitle();
        mDrawerLayout = (DrawerLayout) this.findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) this.findViewById(R.id.left_drawer);

        // set adapter for drawer listview
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_item, mDemoItemTitles));
        // set clicklistener for drawer listview
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                navToDemo(position);
            }
        });

        // Enable ActionBar app icon to behave as action to toggle navigation drawer.
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        // ActionBarDrawerToggle ties together the proper interactions between the sliding drawer
        // and the actionbar app icon.
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.main_drawer_open,
                R.string.main_drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActionBar().setTitle(mCurrentDemoTitle);
                // create call to onPrepareOptionsMenu()
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActionBar().setTitle(R.string.app_name);
                // create call to onPrepareOptionsMenu()
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    /**
     * Load specified fragment to show demo according to user's click.
     *
     * @param position the index in drawer listview.
     */
    private void navToDemo(int position) {
        // Update main content by replacing fragment to show specified demo.
        Fragment demoFragment = null;
        switch (position) {
            case DEMO_INTRODUCTION:
                // TODO: Code a fragment to introduct this demo app
                break;
            case DEMO_FONT_METRICS:
                demoFragment = new MetricsFragment();
                break;
            case DEMO_FONT_TYPEFACE:
                demoFragment = TypefaceFragment.newInstance();
                break;
            case DEMO_SPANNABLE_STRING:
                // TODO: Code a fragment to demo how to use spannableString to customize font&String
                break;
            default:
                break;
        }

        if (demoFragment != null) {
            FragmentTransaction ft = this.getFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, demoFragment);
            ft.commit();

            // Update ActionBar title.
            mCurrentDemoTitle = mDemoItemTitles[position];
        }

        // Close drawer.
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The ActionBar home/up should open or close the navigation drawer,
        // ActionBarDrawerToggle.onOptionsItemSelected() must be called.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        int id = item.getItemId();
        if (id == R.id.action_about) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Pass any configuration change to the drawer toggle.
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // synchronize the indicator with the state of the linked DrawerLayout after
        // onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

}
