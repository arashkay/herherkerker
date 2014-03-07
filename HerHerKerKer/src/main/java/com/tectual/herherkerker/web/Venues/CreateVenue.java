package com.tectual.herherkerker.web.venues;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.gson.GsonFactory;
import com.octo.android.robospice.request.googlehttpclient.GoogleHttpClientSpiceRequest;
import com.tectual.herherkerker.web.data.JsonVenue;

import java.io.IOException;

/**
 * Created by arash on 18/02/2014.
 */
public class CreateVenue extends GoogleHttpClientSpiceRequest<Object> {

    private String baseUrl;
    private JsonVenue json;

    public CreateVenue(String name, String address) {
        super( Object.class );
        json = new JsonVenue();
        json.name = name;
        json.address = address;
        baseUrl = String.format( "http://herherkerker.com/venues/suggest.json" );
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
