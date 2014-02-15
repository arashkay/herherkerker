package com.tectual.herherkerker.models;

import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tectual.herherkerker.R;
import com.tectual.herherkerker.events.LikeEvent;
import com.tectual.herherkerker.events.SharedEvent;

import de.greenrobot.event.EventBus;

/**
 * Created by arash on 7/02/2014.
 */
public class JokeUI {

    public JokeUI(){}

    public View draw(LayoutInflater view, int position, Joke joke, final JokeAdapter adapter){
        final View v = view.inflate(R.layout.joke, null);
        Typeface iconTypeFace = adapter.iconTypeFace;
        Typeface faTypeFace = adapter.faTypeFace;
        TextView body = (TextView) v.findViewById(R.id.body);
        body.setText(joke.getBody());
        TextView likes = (TextView) v.findViewById(R.id.likes);
        likes.setText(joke.getLikes().toString());

        TextView like = (TextView) v.findViewById(R.id.like);
        TextView dislike = (TextView) v.findViewById(R.id.dislike);
        TextView flag = (TextView) v.findViewById(R.id.flag);
        TextView sms = (TextView) v.findViewById(R.id.sms);
        like.setTag(position);
        dislike.setTag(position);
        flag.setTag(position);
        like.setTypeface(iconTypeFace);
        dislike.setTypeface(iconTypeFace);
        flag.setTypeface(iconTypeFace);
        sms.setTypeface(iconTypeFace);
        likes.setTypeface(faTypeFace);
        if(joke.getLiked()){
            like.setVisibility(View.GONE);
        }else {
            dislike.setVisibility(View.GONE);
        }

        sms.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getParent();
                TextView body = (TextView) ((LinearLayout) v.getParent().getParent()).findViewById(R.id.body);
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, body.getText());
                v.getContext().startActivity(Intent.createChooser(sharingIntent, v.getContext().getResources().getString(R.string.sharing_via)));
                EventBus.getDefault().post(new SharedEvent());
            }
        });

        like.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = Integer.parseInt(v.getTag().toString());
                Joke item = (Joke) adapter.getItem(position);
                item.likes = new Integer(item.getLikes().intValue()+1);
                item.liked = true;
                item.save();
                EventBus.getDefault().post(new LikeEvent(item, v, true));
            }
        });

        dislike.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = Integer.parseInt(v.getTag().toString());
                Joke item = (Joke) adapter.getItem(position);
                item.likes = new Integer(item.getLikes().intValue()-1);
                item.liked = false;
                item.save();
                EventBus.getDefault().post(new LikeEvent(item, v, false));
            }
        });


        return v;
    }
}
