package com.tectual.herherkerker.events;

import com.tectual.herherkerker.models.Reward;

import java.util.List;

/**
 * Created by arash on 13/02/2014.
 */
public class RewardsEvent {

    public List<Reward> rewards;

    public RewardsEvent(List<Reward> rewards){
        this.rewards = rewards;
    }
}
