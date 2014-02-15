package com.tectual.herherkerker.events;

import com.tectual.herherkerker.models.Reward;

/**
 * Created by arash on 7/02/2014.
 */
public class UnlockableEvent {

    public Reward reward;
    public boolean accepted;

    public UnlockableEvent(Reward reward, boolean accepted){
        this.reward = reward;
        this.accepted = accepted;
    }

}
