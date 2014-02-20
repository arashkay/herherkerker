package com.tectual.herherkerker.events;

import com.tectual.herherkerker.models.Joke;

/**
 * Created by arash on 2/02/2014.
 */
public class FlagEvent {

    public Joke joke;
    public int position;

    public FlagEvent(Joke joke, int position){
        this.joke = joke;
        this.position = position;
    }

}
