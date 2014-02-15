package com.tectual.herherkerker.web;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.gson.GsonFactory;
import com.octo.android.robospice.request.googlehttpclient.GoogleHttpClientSpiceRequest;
import com.tectual.herherkerker.util.Core;
import com.tectual.herherkerker.web.data.JsonActivity;
import com.tectual.herherkerker.web.data.JsonDevice;
import com.tectual.herherkerker.web.data.JsonRequest;

import java.io.IOException;

/**
 * Created by arash on 15/02/2014.
 */
public class AccountRequest extends GoogleHttpClientSpiceRequest<JsonActivity> {

    private String baseUrl;

    public AccountRequest( Core core, int shares) {
        super( JsonActivity.class );
        baseUrl = String.format( "http://herherkerker.com/devices/show.json?device[did]="+core.device_id+"&shares="+shares );
    }

    @Override
    public JsonActivity loadDataFromNetwork() throws IOException {
        HttpRequest request = getHttpRequestFactory()
                .buildGetRequest( new GenericUrl( baseUrl ) );
        request.setParser( new GsonFactory().createJsonObjectParser() );
        HttpResponse res = request.execute();
        return res.parseAs( getResultType() );
    }
}
