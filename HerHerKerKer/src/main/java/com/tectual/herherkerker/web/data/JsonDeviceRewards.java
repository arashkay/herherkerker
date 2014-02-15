package com.tectual.herherkerker.web.data;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.tectual.herherkerker.models.Reward;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arash on 13/02/2014.
 */
public class JsonDeviceRewards  extends ArrayList<JsonDeviceReward>{

    public List<Reward> persist(){
        List<Reward> rewards = new ArrayList<Reward>();

        ActiveAndroid.beginTransaction();
        try{

            List<Reward> current_rewards = new Select().from(Reward.class).execute();
            ArrayList<Integer> ids = new ArrayList();
            for(Reward reward : current_rewards){
                ids.add(reward.getSid());
            }
            for (JsonDeviceReward element : this) {
                Reward reward = element.toModel();
                if(!ids.contains(reward.getSid())){
                    reward.save();
                    rewards.add(reward);
                }
            }
            ActiveAndroid.setTransactionSuccessful();
        }
        finally {
            ActiveAndroid.endTransaction();
        }

        return rewards;
    }

}
