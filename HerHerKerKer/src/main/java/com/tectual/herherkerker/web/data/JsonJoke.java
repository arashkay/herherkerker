package com.tectual.herherkerker.web.data;

import com.google.api.client.util.Key;
import com.tectual.herherkerker.models.Joke;

/**
 * Created by arash on 27/01/2014.
 */

public class JsonJoke {
    @Key
    private Integer id;
    @Key
    public String body;
    @Key
    private Integer likes;

    public Joke toModel(){
        return new Joke(id,  body, likes, false);
    }
}
