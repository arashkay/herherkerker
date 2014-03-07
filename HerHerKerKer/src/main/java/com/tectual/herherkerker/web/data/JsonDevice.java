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
    double lat;
    @Key
    double lng;

    public JsonDevice(String did){
        this.did = did;
    }

    public JsonDevice(String did, String regid){
        this.did = did;
        this.regid = regid;
    }

    public JsonDevice(String did, double lat, double lng){
        this.did = did;
        this.lat = lat;
        this.lng = lng;
    }

}
