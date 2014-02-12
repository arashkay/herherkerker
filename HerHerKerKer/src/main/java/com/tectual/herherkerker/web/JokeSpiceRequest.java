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
import com.tectual.herherkerker.web.data.JsonJokes;
import com.tectual.herherkerker.web.data.JsonMixedList;
import com.tectual.herherkerker.web.data.JsonRequest;

import java.io.IOException;

/**
 * Created by arash on 27/01/2014.
 */
public class JokeSpiceRequest extends GoogleHttpClientSpiceRequest<JsonMixedList> {

    private String baseUrl;
    private JsonRequest json;

    public JokeSpiceRequest( Core core ) {
        super( JsonMixedList.class );
        JsonDevice device = new JsonDevice(core.device_id);
        json = new JsonRequest(device, core.api, core.version );
        baseUrl = String.format( "http://herherkerker.com/messages/today" );
    }

    @Override
    public JsonMixedList loadDataFromNetwork() throws IOException {
        HttpRequest request = getHttpRequestFactory()
                .buildGetRequest( new GenericUrl( baseUrl ) );
        HttpContent data = new JsonHttpContent( new GsonFactory(), json );
        request.setRequestMethod("POST");
        request.setContent(data);
        request.setParser( new GsonFactory().createJsonObjectParser() );
        HttpResponse res = request.execute();
        return res.parseAs( getResultType() );
    }

}
