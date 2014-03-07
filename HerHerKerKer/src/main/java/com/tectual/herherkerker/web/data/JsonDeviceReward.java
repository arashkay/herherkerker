package com.tectual.herherkerker.web.data;

import com.google.api.client.util.Key;
import com.tectual.herherkerker.models.Reward;

/**
 * Created by arash on 13/02/2014.
 */
public class JsonDeviceReward {
    @Key
    public Integer id;
    @Key
    public String state;
    @Key
    public JsonReward reward;

    public Reward toModel(){
        return new Reward(id,  reward.instruction, "http://www.herherkerker.com"+reward.image_small, "http://www.herherkerker.com"+reward.qrcode, state, reward.expires_at.getValue(), false);
    }
}
