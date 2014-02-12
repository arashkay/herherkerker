package com.tectual.herherkerker;

import android.app.Application;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by arash on 31/01/2014.
 */
public class HerherKerker extends com.activeandroid.app.Application {

    private boolean[] first_load = new boolean[3];

    @Override
    public void onCreate() {
        super.onCreate();
        Arrays.fill(first_load, true);
    }

    public boolean is_first_load(int tab){
        if(first_load[tab]){
            first_load[tab] = false;
            return true;
        }
        return false;
    }


}
