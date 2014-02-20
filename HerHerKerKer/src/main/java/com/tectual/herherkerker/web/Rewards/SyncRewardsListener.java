package com.tectual.herherkerker.web.Rewards;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.tectual.herherkerker.events.JokeEvent;
import com.tectual.herherkerker.models.Joke;
import com.tectual.herherkerker.models.Reward;
import com.tectual.herherkerker.web.data.JsonMixedList;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by arash on 16/02/2014.
 */
public class SyncRewardsListener implements RequestListener<Boolean> {

    List<Reward> rewards;

    public SyncRewardsListener(List<Reward> rewards){
        this.rewards = rewards;
    }

    @Override
    public void onRequestFailure(SpiceException spiceException) {
        //update your UI
        SpiceException ex = spiceException;
    }

    @Override
    public void onRequestSuccess(final Boolean result) {
        ActiveAndroid.beginTransaction();
        if(result){
            try {
                for( Reward reward : rewards){
                    reward.is_changed = false;
                    reward.save();
                }
                ActiveAndroid.setTransactionSuccessful();
            }finally {
                ActiveAndroid.endTransaction();
            }
        }
    }
}