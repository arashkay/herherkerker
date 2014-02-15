package com.tectual.herherkerker.util;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import com.tectual.herherkerker.events.GeoEvent;

import de.greenrobot.event.EventBus;

/**
 * Created by arash on 15/02/2014.
 */
public class GlobalLocationListener implements LocationListener {

    private final Activity activity;
    boolean isNetworkEnabled = false;
    public static long CHANGE_CHECK_INTERVAL = 60000;
    public static long CHANGE_DISTANCE = 100;

    public GlobalLocationListener(Activity a) {
        this.activity = a;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location==null) return;
        EventBus.getDefault().post(new GeoEvent(location.getLatitude(), location.getLongitude()));
    }

}
