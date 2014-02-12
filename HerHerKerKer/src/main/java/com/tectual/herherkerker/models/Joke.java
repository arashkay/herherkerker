package com.tectual.herherkerker.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by arash on 7/01/2014.
 */
@Table(name = "posts")
public class Joke extends Model{

    @Column(name = "sid", index = true)
    public Integer sid;
    @Column(name = "body")
    public String body;
    @Column(name = "likes")
    public Integer likes;
    @Column(name = "liked")
    public Boolean liked;

    public Joke(){
        super();
    }
    public Joke(Integer sid, String body, Integer likes, Boolean liked) {
        super();
        this.sid = sid;
        this.body = body;
        this.likes = likes;
        this.liked = liked;
    }

    public Integer getSid() {
        return sid;
    }

    public String getBody() {
        return body;
    }

    public Integer getLikes() {
        return likes;
    }

    public Boolean getLiked() {
        return liked;
    }
}
