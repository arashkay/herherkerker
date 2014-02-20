package com.tectual.herherkerker.events;

import com.tectual.herherkerker.models.Reward;

/**
 * Created by arash on 16/02/2014.
 */
public class UseEvent {
    public Reward reward;
    public int position;

    public UseEvent(Reward reward, int position){
        this.reward = reward;
        this.position = position;
    }
}
