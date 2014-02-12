package com.tectual.herherkerker.events;

import com.tectual.herherkerker.models.Reward;

/**
 * Created by arash on 7/02/2014.
 */
public class UnlockableEvent {

    public Reward reward;

    public UnlockableEvent(Reward reward){
        this.reward = reward;
    }

}
