package com.tectual.herherkerker.web.jokes;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.gson.GsonFactory;
import com.octo.android.robospice.request.googlehttpclient.GoogleHttpClientSpiceRequest;
import com.tectual.herherkerker.util.Core;
import com.tectual.herherkerker.web.data.JsonDevice;
import com.tectual.herherkerker.web.data.JsonRequest;

import java.io.IOException;

/**
 * Created by arash on 14/02/2014.
 */
public class CreateJoke extends GoogleHttpClientSpiceRequest<Object> {

    private String baseUrl;
    private JsonRequest json;

    public CreateJoke(Core core, String joke) {
        super( Object.class );
        JsonDevice device = new JsonDevice(core.device_id);
        json = new JsonRequest(device, core.api, core.version, joke );
        baseUrl = String.format( "http://herherkerker.com/messages.json" );
    }

    @Override
    public Object loadDataFromNetwork() throws IOException {
        HttpRequest request = getHttpRequestFactory()
                .buildGetRequest( new GenericUrl( baseUrl ) );
        HttpContent data = new JsonHttpContent( new GsonFactory(), json );
        request.setRequestMethod("POST");
        request.setContent(data);
        request.setParser( new GsonFactory().createJsonObjectParser() );
        request.execute();
        return null;
    }

}