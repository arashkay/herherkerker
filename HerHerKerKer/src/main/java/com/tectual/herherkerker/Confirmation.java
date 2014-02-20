package com.tectual.herherkerker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tectual.herherkerker.events.DeleteEvent;
import com.tectual.herherkerker.events.FlagEvent;
import com.tectual.herherkerker.events.NewJokeEvent;
import com.tectual.herherkerker.models.Joke;
import com.tectual.herherkerker.models.Reward;

import de.greenrobot.event.EventBus;

/**
 * Created by arash on 16/02/2014.
 */
public class Confirmation extends AlertDialog.Builder  {

    private AlertDialog dialog;
    private Context context;
    private int type;
    private Joke joke;

    private int position;
    private Reward reward;

    public Confirmation(Context context, Joke joke, int position) {
        super(context);
        this.type = 1;
        this.joke = joke;
        this.position = position;
        Confirmation(context, R.string.flag_confirmation);
    }

    public Confirmation(Context context, Reward reward, int position) {
        super(context);
        this.type = 2;
        this.reward = reward;
        this.position = position;
        Confirmation(context, R.string.reward_delete_confirmation);
    }

    private void Confirmation(Context context, int question) {
        LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.confirmation, null);
        setView(view);
        setTitle(R.string.confirmation);
        TextView text = (TextView) view.findViewById(R.id.confirmation);
        text.setText(question);
        setNegativeButton(R.string.cancel, null);
        setPositiveButton(R.string.yes, null);
        dialog = create();
        dialog.setCancelable(true);
        dialog.show();
        Button button = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        button.setTag(question);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (type){
                    case 1:
                        EventBus.getDefault().post(new FlagEvent(joke, position));
                        break;
                    case 2:
                        EventBus.getDefault().post(new DeleteEvent(reward, position));
                        break;
                }
                dialog.dismiss();
            }
        });
    }
}
