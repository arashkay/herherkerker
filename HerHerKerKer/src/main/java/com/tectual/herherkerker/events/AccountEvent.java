package com.tectual.herherkerker.events;

import com.tectual.herherkerker.web.data.JsonActivity;

/**
 * Created by arash on 15/02/2014.
 */
public class AccountEvent {

    public JsonActivity jsonActivity;

    public AccountEvent(JsonActivity jsonActivity){
        this.jsonActivity = jsonActivity;
    }

}
