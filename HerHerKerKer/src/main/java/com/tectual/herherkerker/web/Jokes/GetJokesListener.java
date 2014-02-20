package com.tectual.herherkerker.web.Jokes;

import android.app.Activity;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.tectual.herherkerker.Jokes;
import com.tectual.herherkerker.R;
import com.tectual.herherkerker.events.JokeEvent;
import com.tectual.herherkerker.models.Joke;
import com.tectual.herherkerker.models.Reward;
import com.tectual.herherkerker.web.data.JsonMixedList;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by arash on 28/01/2014.
 */
public class GetJokesListener implements RequestListener<JsonMixedList> {

    public GetJokesListener(){
    }

    @Override
    public void onRequestFailure(SpiceException spiceException) {
        //update your UI
    }

    @Override
    public void onRequestSuccess(final JsonMixedList jsonJokesRewards) {
        List<Joke> jokes = jsonJokesRewards.jokes.persist();
        List<Reward> rewards = jsonJokesRewards.rewards.toModel();
        EventBus.getDefault().post( new JokeEvent(jokes, rewards));
    }
}
