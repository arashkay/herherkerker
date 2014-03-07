package com.tectual.herherkerker.web.rewards;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.gson.GsonFactory;
import com.octo.android.robospice.request.googlehttpclient.GoogleHttpClientSpiceRequest;
import com.tectual.herherkerker.util.Core;
import com.tectual.herherkerker.web.data.JsonDevice;
import com.tectual.herherkerker.web.data.JsonDeviceRewards;
import com.tectual.herherkerker.web.data.JsonRequest;

import java.io.IOException;

/**
 * Created by arash on 13/02/2014.
 */
public class GetRewards extends GoogleHttpClientSpiceRequest<JsonDeviceRewards> {
    private String baseUrl;
    private JsonRequest json;

    public GetRewards(Core core) {
        super( JsonDeviceRewards.class );
        JsonDevice device = new JsonDevice(core.device_id);
        json = new JsonRequest(device, core.api, core.version );
        baseUrl = String.format( "http://herherkerker.com/devices/rewards" );
    }

    @Override
    public JsonDeviceRewards loadDataFromNetwork() throws IOException {
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
