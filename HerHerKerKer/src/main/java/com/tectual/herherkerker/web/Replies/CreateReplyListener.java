package com.tectual.herherkerker.web.replies;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.tectual.herherkerker.events.UnlockedEvent;
import com.tectual.herherkerker.web.data.JsonReward;

import de.greenrobot.event.EventBus;

/**
 * Created by arash on 9/02/2014.
 */
public class CreateReplyListener implements RequestListener<JsonReward> {

    public CreateReplyListener(){
    }

    @Override
    public void onRequestFailure(SpiceException spiceException) {
        //update your UI
    }

    @Override
    public void onRequestSuccess(JsonReward jsonReward) {
        EventBus.getDefault().post(new UnlockedEvent());
        //new Jokes(activity, activity.getWindow().getDecorView().findViewById(R.id.jokesFragment));
    }
}
