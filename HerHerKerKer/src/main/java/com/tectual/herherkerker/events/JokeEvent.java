package com.tectual.herherkerker.events;

import com.tectual.herherkerker.models.Joke;
import com.tectual.herherkerker.models.Reward;

import java.util.List;

/**
 * Created by arash on 10/02/2014.
 */
public class JokeEvent {

    public List<Joke> jokes;
    public List<Reward> rewards;

    public JokeEvent(List<Joke> jokes, List<Reward> rewards){
        this.jokes = jokes;
        this.rewards = rewards;
    }
}
