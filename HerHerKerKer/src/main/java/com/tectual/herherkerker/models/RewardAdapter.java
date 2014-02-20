package com.tectual.herherkerker.models;

import android.content.Context;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tectual.herherkerker.Confirmation;
import com.tectual.herherkerker.R;

import com.activeandroid.Model;
import com.tectual.herherkerker.events.DeleteEvent;
import com.tectual.herherkerker.events.RedeemEvent;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by arash on 13/02/2014.
 */
public class RewardAdapter extends ArrayAdapter<Reward> {

    private List<Reward> items;
    public Typeface iconTypeFace;
    public Typeface faTypeFace;
    private Context context;

    public RewardAdapter(Context context, int textViewResourceId,
                       List<Reward> items) {
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

        Reward obj = items.get(position);
        final View view = vi.inflate(R.layout.reward, null);

        if (obj != null) {
            Button info = (Button) view.findViewById(R.id.info);
            Button delete = (Button) view.findViewById(R.id.delete);

            info.setTypeface(faTypeFace);
            info.setTag(position);
            delete.setTypeface(faTypeFace);
            delete.setTag(position);

            ImageView image = (ImageView) view.findViewById(R.id.image);
            LinearLayout arrow = (LinearLayout) view.findViewById(R.id.arrow);
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            int width = metrics.widthPixels;
            image.getLayoutParams().height = width/2;
            image.getLayoutParams().width = width/2;
            arrow.getLayoutParams().width = width/2;

            Picasso.with(view.getContext()).load(obj.getImage()).into(image);
            TextView expiry = (TextView) view.findViewById(R.id.expiry);
            if(obj.getState().equals("won")){
                expiry.setText(R.string.winner);
                info.setText(R.string.contact);

                info.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = Integer.parseInt(v.getTag().toString());
                        Reward item = (Reward) getItem(position);
                        EventBus.getDefault().post(new RedeemEvent(item, position, true));
                    }
                });
            }else{
                String duration = getContext().getString(R.string.duration);
                expiry.setText(String.format(duration, obj.getHumanExpiresAt()));

                info.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = Integer.parseInt(v.getTag().toString());
                        Reward item = (Reward) getItem(position);
                        EventBus.getDefault().post(new RedeemEvent(item, position, false));
                    }
                });
            }
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = Integer.parseInt(v.getTag().toString());
                    Reward item = (Reward) getItem(position);
                    new Confirmation(view.getContext(), item, position);
                }
            });
            expiry.setTypeface(faTypeFace);

        }
        return view;
    }

}
