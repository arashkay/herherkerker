package com.tectual.herherkerker.web.data;

import com.google.api.client.util.Key;

import java.util.List;

/**
 * Created by arash on 10/02/2014.
 */
public class JsonRequest {

    @Key
    private String api;
    @Key
    private String version;
    @Key
    private List<String> replies;
    @Key
    private List<JsonReward> rewards;
    @Key
    private JsonReward reward;
    @Key
    private JsonJoke message;
    @Key
    private JsonDevice device;
    @Key
    private int shares;
    @Key
    private int last_reward_id;
    @Key
    private List<Integer> ids;

    public JsonRequest(JsonDevice device, String api, String version){
        basicInfo(device, api, version);
    }

    public JsonRequest(JsonDevice device, String api, String version, List<String> replies, JsonReward reward){
        basicInfo(device, api, version);
        this.replies = replies;
        this.reward = reward;
    }

    private void basicInfo(JsonDevice device, String api, String version){
        this.device = device;
        this.api = api;
        this.version = version;
    }
}
