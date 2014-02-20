package com.tectual.herherkerker.events;

import com.tectual.herherkerker.models.Reward;

/**
 * Created by arash on 16/02/2014.
 */
public class DeleteEvent {
    public Reward reward;
    public int position;

    public DeleteEvent(Reward reward, int position){
        this.reward = reward;
        this.position = position;
    }
}
