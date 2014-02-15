package com.tectual.herherkerker.web.data;

import com.activeandroid.ActiveAndroid;
import com.tectual.herherkerker.models.Reward;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arash on 10/02/2014.
 */
public class JsonRewards extends ArrayList<JsonReward> {

    public List<Reward> toModel(){
        List<Reward> rewards = new ArrayList<Reward>();
        for (JsonReward element : this) {
            rewards.add(element.toModel());
        }
        return rewards;
    }
}
