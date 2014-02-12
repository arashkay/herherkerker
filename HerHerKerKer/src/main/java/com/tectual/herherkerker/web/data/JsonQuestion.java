package com.tectual.herherkerker.web.data;

import com.google.api.client.util.Key;

/**
 * Created by arash on 7/02/2014.
 */
public class JsonQuestion {
    @Key
    public Integer id;
    @Key
    public String title;
    @Key
    public Integer question_type;
    @Key
    public JsonQuestionOption options;

}
