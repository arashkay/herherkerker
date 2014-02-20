package com.tectual.herherkerker.util;

import android.app.Activity;

import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.MapBuilder;
import com.google.analytics.tracking.android.Tracker;
import com.tectual.herherkerker.R;

import java.util.HashMap;

/**
 * Created by arash on 27/01/2014.
 */
public class Analytic {

    private Tracker tracker;
    private Activity activity;

    private static Analytic instance;

    public static Analytic getInstance(Activity activity) {
        if(instance==null)
            instance = new Analytic(activity);
        return instance;
    }

    private Analytic(Activity a) {
        tracker = GoogleAnalytics.getInstance(a).getTracker(a.getResources().getString(R.string.analytic));
        activity = a;
    }

    public void page(String name){
        tracker.send(MapBuilder
                .createAppView()
                .set(Fields.SCREEN_NAME, name)
                .build());
    }

    public void stop(){
        tracker.send(MapBuilder
                .createAppView()
                .set(Fields.SCREEN_NAME, "stop")
                .build());
    }

}
