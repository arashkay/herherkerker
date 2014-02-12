package com.tectual.herherkerker.events;

import android.view.View;

import com.tectual.herherkerker.models.Joke;

/**
 * Created by arash on 2/02/2014.
 */
public class LikeEvent {

    public Joke joke;
    public View view;
    public boolean increased;

    public LikeEvent(Joke joke, View view, boolean increased){
        this.joke = joke;
        this.view = view;
        this.increased = increased;
    }

}
