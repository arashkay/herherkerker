package com.tectual.herherkerker.web.rewards;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.gson.GsonFactory;
import com.octo.android.robospice.request.googlehttpclient.GoogleHttpClientSpiceRequest;
import com.tectual.herherkerker.models.Reward;
import com.tectual.herherkerker.util.Core;
import com.tectual.herherkerker.web.data.JsonDevice;
import com.tectual.herherkerker.web.data.JsonRequest;
import com.tectual.herherkerker.web.data.JsonReward;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by arash on 16/02/2014.
 */
public class SyncRewards extends GoogleHttpClientSpiceRequest<Boolean> {
    private String baseUrl;
    private JsonRequest json;

    public SyncRewards(Core core, List<Reward> rewards) {
        super( Boolean.class );
        JsonDevice device = new JsonDevice(core.device_id);
        List<JsonReward> list = new ArrayList<JsonReward>();
        for(Reward reward : rewards){
            list.add(reward.toJSON());
        }
        json = new JsonRequest(device, core.api, core.version, list);
        baseUrl = String.format( "http://herherkerker.com/device_rewards/sync.json"  );
    }

    @Override
    public Boolean loadDataFromNetwork() throws IOException {
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
