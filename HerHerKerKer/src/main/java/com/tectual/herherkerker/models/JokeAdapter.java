package com.tectual.herherkerker.models;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.activeandroid.Model;
import com.squareup.picasso.Picasso;
import com.tectual.herherkerker.R;
import com.tectual.herherkerker.events.LikeEvent;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by arash on 7/01/2014.
 */
public class JokeAdapter extends ArrayAdapter<Model> {

    private List<Model> items;
    public Typeface iconTypeFace;
    public Typeface faTypeFace;
    private Context context;

    public JokeAdapter(Context context, int textViewResourceId,
                       List<Model> items) {
        super(context, textViewResourceId, items);
        this.context = context;
        this.items = items;
        iconTypeFace= Typeface.createFromAsset(context.getAssets(),"fontawesome.ttf");
        faTypeFace= Typeface.createFromAsset(context.getAssets(),"Arshia.ttf");
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Model obj = items.get(position);
        if (obj != null) {
            if(obj instanceof Joke){
                v = new JokeUI().draw(vi, position, (Joke) obj, this);
            }else{
                v = new RewardUI().draw(vi, position, (Reward) obj, this, context);
            }
        }

        return v;
    }

}
