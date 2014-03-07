package com.tectual.herherkerker;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.octo.android.robospice.SpiceManager;
import com.tectual.herherkerker.web.venues.CreateVenue;
import com.tectual.herherkerker.web.VoidRequestListener;

/**
 * Created by arash on 19/02/2014.
 */
public class Checkins {

    private MainActivity activity;
    private View view;
    private SpiceManager spiceManager;

    public Checkins(){}

    public void start(MainActivity a, View v){
        activity = a;
        view = v;
        spiceManager = activity.spiceManager;
        Button button = (Button) view.findViewById(R.id.submit_venue);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String name = ((EditText) view.findViewById(R.id.venue_name)).getText().toString();
                String address = ((EditText) view.findViewById(R.id.venue_address)).getText().toString();
                if(!name.isEmpty()){
                    CreateVenue request = new CreateVenue(name, address);
                    spiceManager.execute(request, new VoidRequestListener());
                    Toast.makeText(activity.getApplicationContext(), R.string.venue_is_posted,
                            Toast.LENGTH_LONG).show();
                    ((EditText) view.findViewById(R.id.venue_name)).setText("");
                    ((EditText) view.findViewById(R.id.venue_address)).setText("");
                }
            }
        });
    }

}
