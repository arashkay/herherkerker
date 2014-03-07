package com.tectual.herherkerker.web.devices;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.json.gson.GsonFactory;
import com.octo.android.robospice.request.googlehttpclient.GoogleHttpClientSpiceRequest;
import com.tectual.herherkerker.util.Core;
import com.tectual.herherkerker.web.data.JsonActivity;

import java.io.IOException;

/**
 * Created by arash on 15/02/2014.
 */
public class GetDevice extends GoogleHttpClientSpiceRequest<JsonActivity> {

    private String baseUrl;

    public GetDevice(Core core, int shares) {
        super( JsonActivity.class );
        baseUrl = String.format( "http://herherkerker.com/devices/show.json?api="+core.api+"&version="+core.version+"&device[did]="+core.device_id+"&shares="+shares );
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
