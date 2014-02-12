package com.tectual.herherkerker.web.data;

import com.google.api.client.util.Key;

/**
 * Created by arash on 10/02/2014.
 */
public class JsonDevice {

    @Key
    String did;
    @Key
    String regid;
    @Key
    String lat;
    @Key
    String lng;

    public JsonDevice(String did){
        this.did = did;
        this.regid = regid;
    }

}
