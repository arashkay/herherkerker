package com.tectual.herherkerker.web.Rewards;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.tectual.herherkerker.events.JokeEvent;
import com.tectual.herherkerker.events.RewardsEvent;
import com.tectual.herherkerker.models.Joke;
import com.tectual.herherkerker.models.Reward;
import com.tectual.herherkerker.web.data.JsonDeviceRewards;
import com.tectual.herherkerker.web.data.JsonMixedList;
import com.tectual.herherkerker.web.data.JsonQuestions;
import com.tectual.herherkerker.web.data.JsonRewards;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by arash on 13/02/2014.
 */
public class GetRewardsListener implements RequestListener<JsonDeviceRewards> {

    public GetRewardsListener(){
    }

    @Override
    public void onRequestFailure(SpiceException spiceException) {
        //update your UI
    }

    @Override
    public void onRequestSuccess(final JsonDeviceRewards jsonDeviceRewards) {
        List<Reward> rewards = jsonDeviceRewards.persist();
        EventBus.getDefault().post( new RewardsEvent(rewards));
    }
}
