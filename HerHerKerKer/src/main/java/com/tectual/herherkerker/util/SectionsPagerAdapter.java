package com.tectual.herherkerker.util;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tectual.herherkerker.Checkins;
import com.tectual.herherkerker.Jokes;
import com.tectual.herherkerker.R;
import com.tectual.herherkerker.Wallet;

import java.util.Locale;

/**
 * Created by arash on 25/01/2014.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    public Jokes jokes;
    public Wallet wallet;
    public Checkins checkins;

    public SectionsPagerAdapter(FragmentManager fm, Context c) {
        super(fm);
        context = c;
        wallet = new Wallet();
        jokes = new Jokes();
        checkins = new Checkins();
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new SectionFragment(wallet, jokes, checkins);
        Bundle args = new Bundle();
        args.putInt(SectionFragment.ARG_SECTION_NUMBER, position + 1);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        switch (position) {
            case 0:
                return context.getString(R.string.tab1);
            case 1:
                return context.getString(R.string.tab2);
            case 2:
                return context.getString(R.string.tab3);
        }
        return null;
    }

}