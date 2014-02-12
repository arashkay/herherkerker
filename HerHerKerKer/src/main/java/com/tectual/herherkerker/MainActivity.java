package com.tectual.herherkerker;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tectual.herherkerker.util.Analytic;
import com.tectual.herherkerker.util.Core;
import com.tectual.herherkerker.util.SectionsPagerAdapter;

import com.tectual.herherkerker.util.Storage;


public class MainActivity extends FragmentActivity implements ActionBar.TabListener {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private LinearLayout mDrawerList;
    private ViewPager mViewPager;
    private Core core;
    private Storage storage;
    private Analytic analytic;
    private Typeface iconTypeFace;
    private Typeface faTypeFace;
    private boolean doubleBackToExitPressedOnce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        iconTypeFace = Typeface.createFromAsset(getAssets(),"fontawesome.ttf");
        faTypeFace = Typeface.createFromAsset(getAssets(), "Arshia.ttf");
        header();
        drawer();
        core = Core.getInstance(this);
        storage = Storage.getInstance(this);
        analytic = Analytic.getInstance(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        analytic.page(getActionBar().getSelectedTab().getText().toString());
    }

    @Override
    public void onStop() {
        analytic.stop();
        super.onStop();
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
        mViewPager.setOffscreenPageLimit(2);
        if(analytic!=null)
            analytic.page(tab.getText().toString());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ( keyCode == KeyEvent.KEYCODE_MENU ) {
            mDrawerLayout.openDrawer(Gravity.LEFT);
            if(mDrawerLayout.isDrawerOpen(mDrawerList)){
                mDrawerLayout.closeDrawer(Gravity.LEFT);
            }else{
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
            return true;
        }else if(keyCode == KeyEvent.KEYCODE_BACK){
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return true;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast toast = Toast.makeText(getApplicationContext(), R.string.exiting,
                    Toast.LENGTH_SHORT);
            ((TextView)((LinearLayout) toast.getView()).getChildAt(0)).setGravity(Gravity.CENTER_HORIZONTAL);
            toast.show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() { doubleBackToExitPressedOnce=false; }
            }, 2000);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void header(){
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflator.inflate(R.layout.title, null);
        TextView title = (TextView) view.findViewById(R.id.title);
        title.setTypeface(faTypeFace);
        title.setText(this.getTitle());
        actionBar.setCustomView(view);

        view.findViewById(R.id.avatar).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), ProfileActivity.class));
            }
        });

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), this);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            View tabView = getLayoutInflater().inflate(R.layout.tab, null);
            TextView tabText = (TextView) tabView.findViewById(R.id.tab_name);
            tabText.setText(mSectionsPagerAdapter.getPageTitle(i));
            tabText.setTypeface(faTypeFace);
            actionBar.addTab(
                    actionBar.newTab()
                            .setCustomView(tabView)
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }

    private void drawer(){
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (LinearLayout) findViewById(R.id.drawer_menu);

        for (int i = 0; i < mDrawerList.getChildCount(); i++) {
            ((LinearLayout) mDrawerList.getChildAt(i)).setOnClickListener(new DrawerItemClickListener());
        }
        ((TextView) mDrawerList.findViewById(R.id.drawer_joke_label)).setTypeface(faTypeFace);
        ((TextView) mDrawerList.findViewById(R.id.drawer_joke_icon)).setTypeface(iconTypeFace);
        ((TextView) mDrawerList.findViewById(R.id.drawer_fans_label)).setTypeface(faTypeFace);
        ((TextView) mDrawerList.findViewById(R.id.drawer_fans_icon)).setTypeface(iconTypeFace);
        ((TextView) mDrawerList.findViewById(R.id.drawer_rewards_label)).setTypeface(faTypeFace);
        ((TextView) mDrawerList.findViewById(R.id.drawer_rewards_icon)).setTypeface(iconTypeFace);
        ((TextView) mDrawerList.findViewById(R.id.drawer_credits_label)).setTypeface(faTypeFace);
        ((TextView) mDrawerList.findViewById(R.id.drawer_credits_icon)).setTypeface(iconTypeFace);

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
            );

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        mDrawerToggle.syncState();
    }

    private class DrawerItemClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String tag = v.getTag().toString();
            if (tag.equals("business")) {
                PopupPageBuilder popupPage = new PopupPageBuilder(v.getContext(), R.layout.business, R.string.drawer_rewards);
            } else if (tag.equals("about")) {
                PopupPageBuilder popupPage = new PopupPageBuilder(v.getContext(), R.layout.about_us, R.string.drawer_about);
            } else if (tag.equals("joke")) {
            } else if (tag.equals("facebook")) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/herherkerkerapp")));
            }
            mDrawerLayout.closeDrawer(mDrawerList);
        }

    }

}