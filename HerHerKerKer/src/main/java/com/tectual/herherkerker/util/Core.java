package com.tectual.herherkerker.util;

import android.app.Activity;
import android.provider.Settings.Secure;

import com.tectual.herherkerker.R;

/**
 * Created by arash on 27/01/2014.
 */
public class Core {

    private static Core instance;
    public String device_id;
    public String api;
    public String version;

    public static Core getInstance(Activity activity) {
        if(instance==null)
            instance = new Core(activity);
        return instance;
    }

    private Core(Activity activity) {
        device_id = "6"; //Secure.getString(activity.getContentResolver(), Secure.ANDROID_ID);
        api = activity.getString(R.string.api);
        version = activity.getString(R.string.app_version);
    }
}
