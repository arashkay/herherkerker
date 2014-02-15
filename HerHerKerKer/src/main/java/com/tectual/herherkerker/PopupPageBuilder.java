package com.tectual.herherkerker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tectual.herherkerker.events.NewJokeEvent;
import com.tectual.herherkerker.util.Core;
import com.tectual.herherkerker.util.Storage;

import java.util.Set;
import java.util.zip.Inflater;

import de.greenrobot.event.EventBus;


/**
 * Created by arash on 8/02/2014.
 */
public class PopupPageBuilder extends AlertDialog.Builder  {

    private final AlertDialog dialog;
    private Context context;

    public PopupPageBuilder(Context context, int layout, String title) {
        super(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layout, null);
        setView(view);
        setTitle(title);

        setNegativeButton(R.string.close, null);
        if(layout==R.layout.new_joke){
            setPositiveButton(R.string.submit, null);
        }
        dialog = create();
        dialog.setCancelable(true);
        dialog.show();
        if(layout==R.layout.business){
            business(view);
        }else if(layout==R.layout.about_us){
            about(view);
        }else if(layout==R.layout.new_joke){
            new_joke(view);
        }else if(layout==R.layout.badges){
            badges(view);
        }
    }

    private void business(final View view){
        Button button = (Button) view.findViewById(R.id.order_reward);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setType("message/rfc822");
                Uri data = Uri.parse("mailto:app@tectual.com.au?subject=" + view.getContext().getString(R.string.order_reward_email_title) + "&body=" + view.getContext().getString(R.string.order_reward_email_text));
                intent.setData(data);
                view.getContext().startActivity(intent);
            }
        });
    }

    private void about(final View view){
        TextView id = (TextView) view.findViewById(R.id.device_id);
        String device_id = Core.getInstance((Activity) context).device_id;
        id.setText(device_id);
    }

    private void new_joke(final View view){
        Button button = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                EditText input = (EditText) view.findViewById(R.id.joke_input);
                String joke = input.getText().toString();
                if(!joke.isEmpty()&&joke.length()>15){
                    EventBus.getDefault().post(new NewJokeEvent(joke));
                }
                dialog.dismiss();
            }
        });
    }

    private void badges(final View view){
        Set<String> badges = Storage.getInstance((MainActivity) context).badges();
        if(badges.contains("day5"))
            ((LinearLayout) view.findViewById(R.id.active1)).setAlpha('1');
        if(badges.contains("shared10"))
            ((LinearLayout) view.findViewById(R.id.social1)).setAlpha('1');
        if(badges.contains("liked100"))
            ((LinearLayout) view.findViewById(R.id.like1)).setAlpha('1');
        if(badges.contains("sent25"))
            ((LinearLayout) view.findViewById(R.id.active2)).setAlpha('1');
        if(badges.contains("shared30"))
            ((LinearLayout) view.findViewById(R.id.social2)).setAlpha('1');
        if(badges.contains("liked500"))
            ((LinearLayout) view.findViewById(R.id.like2)).setAlpha('1');
        if(badges.contains("liked1200"))
            ((LinearLayout) view.findViewById(R.id.like3)).setAlpha('1');
        if(badges.contains("shared100"))
            ((LinearLayout) view.findViewById(R.id.social3)).setAlpha('1');
    }

}
