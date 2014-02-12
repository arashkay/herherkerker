package com.tectual.herherkerker.web;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.gson.GsonFactory;
import com.octo.android.robospice.request.googlehttpclient.GoogleHttpClientSpiceRequest;
import com.tectual.herherkerker.util.Core;
import com.tectual.herherkerker.web.data.JsonDevice;
import com.tectual.herherkerker.web.data.JsonRequest;
import com.tectual.herherkerker.web.data.JsonReward;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by arash on 9/02/2014.
 */
public class ReplyRequest extends GoogleHttpClientSpiceRequest<JsonReward> {

    private String baseUrl;
    private JsonRequest json;

    public ReplyRequest( int id, Core core, List<String> answers, JsonReward reward) {
        super( JsonReward.class );
        JsonDevice device = new JsonDevice(core.device_id);
        json = new JsonRequest(device, core.api, core.version, answers, reward);
        baseUrl = String.format( String.format("http://herherkerker.com/questions/%02d/reply.json", id) );
    }

    @Override
    public JsonReward loadDataFromNetwork() throws IOException {
        HttpRequest request = getHttpRequestFactory()
                .buildGetRequest(new GenericUrl(baseUrl));
        HttpContent data = new JsonHttpContent( new GsonFactory(), json );
        request.setRequestMethod("POST");
        request.setContent(data);
        request.setParser(new GsonFactory().createJsonObjectParser());
        HttpResponse res = request.execute();
        return res.parseAs( getResultType() );
    }
}
