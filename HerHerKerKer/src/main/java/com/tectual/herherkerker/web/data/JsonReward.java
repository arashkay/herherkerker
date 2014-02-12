package com.tectual.herherkerker.web.data;

import com.google.api.client.util.DateTime;
import com.google.api.client.util.Key;
import com.tectual.herherkerker.models.Joke;
import com.tectual.herherkerker.models.Reward;

/**
 * Created by arash on 28/01/2014.
 */
public class JsonReward {
    @Key
    public Integer id;
    @Key
    public String instruction;
    @Key
    public String image_small;
    @Key
    public String qrcode;
    @Key
    public String state;
    @Key
    public DateTime expires_at;
    @Key
    public Boolean is_changed;

    public Reward toModel(){
        return new Reward(id,  instruction, "http://www.herherkerker.com"+image_small, qrcode, state, expires_at, false);
    }
}
