package com.tectual.herherkerker.web.devices;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.tectual.herherkerker.events.AccountEvent;
import com.tectual.herherkerker.web.data.JsonActivity;

import de.greenrobot.event.EventBus;

/**
 * Created by arash on 15/02/2014.
 */
public class GetDeviceListener implements RequestListener<JsonActivity> {

    public GetDeviceListener(){
    }

    @Override
    public void onRequestFailure(SpiceException spiceException) {
        //update your UI
        SpiceException ss = spiceException;
    }

    @Override
    public void onRequestSuccess(final JsonActivity jsonActivity) {
        EventBus.getDefault().post( new AccountEvent(jsonActivity));
    }
}
