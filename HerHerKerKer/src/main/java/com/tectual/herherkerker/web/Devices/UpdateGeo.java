package com.tectual.herherkerker.web.Devices;

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

import java.io.IOException;

/**
 * Created by arash on 15/02/2014.
 */
public class UpdateGeo extends GoogleHttpClientSpiceRequest<Object> {

    private String baseUrl;
    private JsonRequest json;

    public UpdateGeo(Core core, double lat, double lng) {
        super( Object.class );
        JsonDevice device = new JsonDevice(core.device_id, lat, lng);
        json = new JsonRequest(device, core.api, core.version );
        baseUrl = String.format( "http://herherkerker.com/devices/geo.json" );
    }

    @Override
    public Object loadDataFromNetwork() throws IOException {
        HttpRequest request = getHttpRequestFactory()
                .buildGetRequest( new GenericUrl( baseUrl ) );
        HttpContent data = new JsonHttpContent( new GsonFactory(), json );
        request.setRequestMethod("POST");
        request.setContent(data);
        request.setParser( new GsonFactory().createJsonObjectParser() );
        HttpResponse res = request.execute();
        return null;
    }
}
