package com.tectual.herherkerker.web.jokes;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.json.gson.GsonFactory;
import com.octo.android.robospice.request.googlehttpclient.GoogleHttpClientSpiceRequest;

import java.io.IOException;

/**
 * Created by arash on 2/02/2014.
 */
public class LikeJoke extends GoogleHttpClientSpiceRequest<Object> {

    private String baseUrl;

    public LikeJoke(int sid) {
        super( Object.class );
        baseUrl = String.format( String.format("http://herherkerker.com/messages/%02d/like.json", sid) );
    }

    @Override
    public Object loadDataFromNetwork() throws IOException {
        HttpRequest request = getHttpRequestFactory()
                .buildGetRequest( new GenericUrl( baseUrl ) );
        request.setRequestMethod("POST");
        request.setParser( new GsonFactory().createJsonObjectParser() );
        request.execute();
        return null;
    }

}