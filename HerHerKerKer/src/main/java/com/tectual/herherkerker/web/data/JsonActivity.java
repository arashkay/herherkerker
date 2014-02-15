package com.tectual.herherkerker.web.data;

import com.google.api.client.util.Key;

import java.util.List;

/**
 * Created by arash on 15/02/2014.
 */
public class JsonActivity {

    @Key
    public int likes;
    @Key
    public List<String> badges;

}
