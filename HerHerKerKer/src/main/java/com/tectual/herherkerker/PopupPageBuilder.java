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
import android.widget.TextView;

import com.tectual.herherkerker.events.NewJokeEvent;
import com.tectual.herherkerker.util.Core;

import java.util.zip.Inflater;

import de.greenrobot.event.EventBus;


/**
 * Created by arash on 8/02/2014.
 */
public class PopupPageBuilder extends AlertDialog.Builder  {

    private final AlertDialog dialog;
    private Context context;

    public PopupPageBuilder(Context context, int layout, int title) {
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


}
