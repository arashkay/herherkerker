package com.tectual.herherkerker.models;

import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tectual.herherkerker.R;
import com.tectual.herherkerker.events.UnlockableEvent;

import de.greenrobot.event.EventBus;

/**
 * Created by arash on 7/02/2014.
 */
public class RewardUI {

    public RewardUI(){}

    public View draw(LayoutInflater view, int position, Reward reward, final JokeAdapter adapter, Context context){
        final View v = view.inflate(R.layout.reward_inline, null);
        Typeface faTypeFace = adapter.faTypeFace;
        Button get = (Button) v.findViewById(R.id.get_reward);
        Button reject = (Button) v.findViewById(R.id.reject_reward);

        get.setTypeface(faTypeFace);
        get.setTag(position);
        reject.setTypeface(faTypeFace);
        reject.setTag(position);

        ImageView image = (ImageView) v.findViewById(R.id.image);
        LinearLayout arrow = (LinearLayout) v.findViewById(R.id.arrow);
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        image.getLayoutParams().height = width/2;
        image.getLayoutParams().width = width/2;
        arrow.getLayoutParams().width = width/2;

        TextView instruction = (TextView) v.findViewById(R.id.instruction);
        Picasso.with(v.getContext()).load(reward.getImage()).into(image);
        instruction.setText(reward.getInstruction());

        get.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = Integer.parseInt(v.getTag().toString());
                Reward item = (Reward) adapter.getItem(position);
                EventBus.getDefault().post(new UnlockableEvent(item, true));
            }
        });

        reject.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = Integer.parseInt(v.getTag().toString());
                Reward item = (Reward) adapter.getItem(position);
                EventBus.getDefault().post(new UnlockableEvent(item, false));
            }
        });

        return v;
    }
}
