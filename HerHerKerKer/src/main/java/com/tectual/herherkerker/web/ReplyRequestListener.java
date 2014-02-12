package com.tectual.herherkerker.web;

import android.app.Activity;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.tectual.herherkerker.web.data.JsonReward;

/**
 * Created by arash on 9/02/2014.
 */
public class ReplyRequestListener implements RequestListener<JsonReward> {

    public ReplyRequestListener(){
    }

    @Override
    public void onRequestFailure(SpiceException spiceException) {
        //update your UI
    }

    @Override
    public void onRequestSuccess(final JsonReward jsonReward) {
        //jsonReward.persist();
        JsonReward js = jsonReward;
        //new Jokes(activity, activity.getWindow().getDecorView().findViewById(R.id.jokesFragment));
    }
}
