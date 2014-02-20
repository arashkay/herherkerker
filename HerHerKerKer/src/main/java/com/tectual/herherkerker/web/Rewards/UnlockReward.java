package com.tectual.herherkerker.web.Rewards;

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
import com.tectual.herherkerker.web.data.JsonQuestions;
import com.tectual.herherkerker.web.data.JsonRequest;

import org.apache.commons.io.IOUtils;

import java.io.IOException;

/**
 * Created by arash on 7/02/2014.
 */
public class UnlockReward extends GoogleHttpClientSpiceRequest<JsonQuestions> {

    private String baseUrl;
    private JsonRequest json;

    public UnlockReward(int id, Core core) {
        super( JsonQuestions.class );
        JsonDevice device = new JsonDevice(core.device_id);
        json = new JsonRequest(device, core.api, core.version);
        baseUrl = String.format( "http://herherkerker.com/rewards/%d/unlock.json", id );
    }

    @Override
    public JsonQuestions loadDataFromNetwork() throws IOException {
        HttpRequest request = getHttpRequestFactory()
                .buildGetRequest( new GenericUrl( baseUrl ) );
        HttpContent data = new JsonHttpContent( new GsonFactory(), json );
        request.setRequestMethod("POST");
        request.setContent(data);
        request.setParser(new GsonFactory().createJsonObjectParser());
        HttpResponse res = request.execute();
        return res.parseAs( getResultType() );
    }

}
