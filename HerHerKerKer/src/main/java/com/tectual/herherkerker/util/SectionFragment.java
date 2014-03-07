package com.tectual.herherkerker.util;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tectual.herherkerker.Checkins;
import com.tectual.herherkerker.MainActivity;
import com.tectual.herherkerker.R;
import com.tectual.herherkerker.Jokes;
import com.tectual.herherkerker.Wallet;

import de.greenrobot.event.EventBus;

/**
 * Created by arash on 25/01/2014.
 */
public class SectionFragment extends Fragment {

    public static final String ARG_SECTION_NUMBER = "section_number";

    private Wallet wallet;
    private Jokes jokes;
    private Checkins checkins;

    public SectionFragment(Wallet wallet, Jokes jokes, Checkins checkins) {
        this.wallet = wallet;
        this.jokes = jokes;
        this.checkins = checkins;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MainActivity activity = (MainActivity) getActivity();
        View view;
        switch (getArguments().getInt(ARG_SECTION_NUMBER)){
            case 3:
                view = inflater.inflate(R.layout.checkins, container, false);
                checkins.start(activity, view);
                break;
            case 2:
                view = inflater.inflate(R.layout.wallet, container, false);
                wallet.start(activity, view);
                break;
            default:
                view = inflater.inflate(R.layout.jokes, container, false);
                jokes.start(activity, view);
                break;
        }
        return view;
    }

}