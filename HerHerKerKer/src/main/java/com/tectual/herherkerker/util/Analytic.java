package com.tectual.herherkerker.util;

import android.app.Activity;

import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.MapBuilder;

/**
 * Created by arash on 27/01/2014.
 */
public class Analytic {

    private EasyTracker tracker;
    private Activity activity;

    private static Analytic instance;

    public static Analytic getInstance(Activity activity) {
        if(instance==null)
            instance = new Analytic(activity);
        return instance;
    }

    private Analytic(Activity a) {
        tracker = EasyTracker.getInstance(a);
        activity = a;
    }

    public void page(String name){
        tracker.set(Fields.SCREEN_NAME, name);
        tracker.send(MapBuilder
                .createAppView()
                .build());
    }

    public void stop(){
        tracker.set(Fields.SCREEN_NAME, "stop");
        tracker.send(MapBuilder
                .createAppView()
                .build());
    }

}
