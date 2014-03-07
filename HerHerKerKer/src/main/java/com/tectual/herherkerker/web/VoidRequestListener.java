package com.tectual.herherkerker.web;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

/**
 * Created by arash on 2/02/2014.
 */
public class VoidRequestListener implements RequestListener<Object> {


    public VoidRequestListener(){

    }

    @Override
    public void onRequestFailure(SpiceException spiceException) {
        //update your UI
    }

    @Override
    public void onRequestSuccess(final Object object) {
        //do nothing
    }

}
