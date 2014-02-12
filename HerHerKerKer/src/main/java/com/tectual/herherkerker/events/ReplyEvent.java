package com.tectual.herherkerker.events;

import com.tectual.herherkerker.web.data.JsonQuestion;

import java.util.List;

/**
 * Created by arash on 9/02/2014.
 */
public class ReplyEvent {

    public JsonQuestion question;
    public List<String> answer;

    public ReplyEvent(JsonQuestion question, List<String> answer){
        this.question = question;
        this.answer = answer;
    }

}
