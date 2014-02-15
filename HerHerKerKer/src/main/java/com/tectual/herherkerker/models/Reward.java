package com.tectual.herherkerker.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.api.client.util.DateTime;
import com.tectual.herherkerker.web.data.JsonReward;

import java.util.Date;
import org.joda.time.Days;

/**
 * Created by arash on 3/02/2014.
 */

@Table(name = "rewards")
public class Reward extends Model {

    @Column(name = "sid", index = true)
    public Integer sid;
    @Column(name = "instruction")
    public String instruction;
    @Column(name = "expires_at")
    public long expires_at;
    @Column(name = "image")
    public String image;
    @Column(name = "state")
    public String state;
    @Column(name = "qr_code")
    public String qr_code;
    @Column(name = "is_changed")
    public Boolean is_changed;

    public Reward(){
        super();
    }
    public Reward(Integer sid, String instruction, String image, String qr_code, String state, long expires_at, Boolean is_changed) {
        super();
        this.sid = sid;
        this.instruction = instruction;
        this.expires_at = expires_at;
        this.image = image;
        this.qr_code = qr_code;
        this.state = state;
        this.is_changed = is_changed;
    }
    public JsonReward toJSON(){
        JsonReward reward = new JsonReward();
        reward.id = sid;
        return reward;
    }

    public Integer getSid() {
        return sid;
    }

    public String getInstruction() {
        return instruction;
    }

    public long getExpiresAt() {
        return expires_at;
    }

    public int getHumanExpiresAt() {
        org.joda.time.DateTime now = new org.joda.time.DateTime();
        org.joda.time.DateTime date = new org.joda.time.DateTime(expires_at);
        int days =Days.daysBetween(now, date).getDays();
        return days;
    }

    public String getImage() {
        return image;
    }

    public String getQrCode() {
        return qr_code;
    }

    public String getState() {
        return state;
    }
}
