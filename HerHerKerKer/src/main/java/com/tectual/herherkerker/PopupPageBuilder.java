package com.tectual.herherkerker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import java.util.zip.Inflater;


/**
 * Created by arash on 8/02/2014.
 */
public class PopupPageBuilder extends AlertDialog.Builder  {

    public PopupPageBuilder(Context context, int layout, int title) {
        super(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layout, null);
        setView(view);
        setTitle(title);
        setPositiveButton(R.string.close, null);
        if(layout==R.layout.business){
            business(view);
        }
        show();
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
}
