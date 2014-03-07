package com.tectual.herherkerker.web.rewards;

import android.app.Activity;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.tectual.herherkerker.QuestionBuilder;
import com.tectual.herherkerker.events.UnlockedEvent;
import com.tectual.herherkerker.web.data.JsonQuestions;

import de.greenrobot.event.EventBus;

/**
 * Created by arash on 7/02/2014.
 */
public class UnlockRewardListener implements RequestListener<JsonQuestions> {

    private Activity activity;

    public UnlockRewardListener(Activity a){
        activity = a;
    }

    @Override
    public void onRequestFailure(SpiceException spiceException) {
        //update your UI
        SpiceException ex = spiceException;
    }

    @Override
    public void onRequestSuccess(final JsonQuestions jsonQuestions) {
        if(jsonQuestions.isEmpty()){
            EventBus.getDefault().post(new UnlockedEvent());
        }else
            new QuestionBuilder( activity, jsonQuestions);
    }
}
