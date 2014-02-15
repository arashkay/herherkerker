package com.tectual.herherkerker.util;

import android.app.Activity;
import android.content.SharedPreferences;

import com.tectual.herherkerker.R;

import java.util.Set;

/**
 * Created by arash on 28/01/2014.
 */
public class Storage {

    private static final String STORAGE = "profile";
    private static final String SHARES_KEY = "shares";
    private static final String POINTS_KEY = "points";
    private static final String BADGES_KEY = "badges";
    private static final String REWARDS_KEY = "last_reward";
    private static final String NOTIFICATION_KEY = "notification";

    public int shares;
    public int reward;
    public int points;
    public Set<String> badges;
    public String notification;


    private static Storage instance;
    private SharedPreferences settings;

    public static Storage getInstance(Activity activity) {
        if(instance==null)
            instance = new Storage(activity);
        return instance;
    }

    private Storage(Activity activity) {
        settings = activity.getSharedPreferences(STORAGE, 0);
        notification = notification();
        shares = shares();
        points = points();
        badges = badges();
    }

    public int shares(int value){
        shares = putInt(SHARES_KEY, value);
        return shares;
    }

    public int shares(){
        return settings.getInt(SHARES_KEY, 0);
    }

    public int points(int value){
        points = putInt(POINTS_KEY, value);
        return points;
    }

    public int points(){
        return settings.getInt(POINTS_KEY, 0);
    }

    public Set<String> badges(Set<String> value){
        badges = putStringSet(BADGES_KEY, value);
        return badges;
    }

    public Set<String> badges(){
        return settings.getStringSet(BADGES_KEY, null);
    }

    public int rewards(int value){
        reward = putInt(REWARDS_KEY, value);
        return reward;
    }

    public int rewards(){
        return settings.getInt(REWARDS_KEY, 0);
    }

    public String notification(String value){
        notification = putString(NOTIFICATION_KEY, value);
        return notification;
    }

    public String notification(){
        return settings.getString(NOTIFICATION_KEY, null);
    }

    private int putInt(String key, int value){
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        editor.commit();
        return value;
    }

    private String putString(String key, String value){
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        editor.commit();
        return value;
    }

    private Set<String> putStringSet(String key, Set<String> value){
        SharedPreferences.Editor editor = settings.edit();
        editor.putStringSet(key, value);
        editor.commit();
        return value;
    }
}
