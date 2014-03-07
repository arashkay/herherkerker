package com.tectual.herherkerker.web.rewards;

import com.activeandroid.ActiveAndroid;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.tectual.herherkerker.models.Reward;

import java.util.List;

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
        if(result){
            ActiveAndroid.beginTransaction();
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