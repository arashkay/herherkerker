package com.tectual.herherkerker;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.tectual.herherkerker.events.ReplyEvent;
import com.tectual.herherkerker.models.JokeAdapter;
import com.tectual.herherkerker.models.QuestionAdapter;
import com.tectual.herherkerker.web.data.JsonQuestion;
import com.tectual.herherkerker.web.data.JsonQuestions;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by arash on 7/02/2014.
 */
public class QuestionBuilder extends AlertDialog.Builder {

    private JsonQuestion question;
    private Activity mActivity;
    private View dialoglayout;

    public QuestionBuilder(Activity activity, final JsonQuestions questions) {
        super(activity);
        this.mActivity = activity;
        this.question = questions.get(0);
        this.setTitle(R.string.question);
        LayoutInflater inflater = activity.getLayoutInflater();
        switch (question.question_type){
            case 1:
                dialoglayout = inflater.inflate(R.layout.input_question, null);
                break;
            case 2:
                dialoglayout = inflater.inflate(R.layout.multi_question, null);
                final ListView multilistview = (ListView) dialoglayout.findViewById(R.id.options);
                final QuestionAdapter multiadapter = new QuestionAdapter(activity,
                        android.R.layout.simple_list_item_1, question.options.answers, false);
                multilistview.setAdapter(multiadapter);
                break;
            case 3:
                dialoglayout = inflater.inflate(R.layout.single_question, null);
                final ListView listview = (ListView) dialoglayout.findViewById(R.id.options);
                final QuestionAdapter adapter = new QuestionAdapter(activity,
                        android.R.layout.simple_list_item_1, question.options.answers, true);
                listview.setAdapter(adapter);
                listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                break;
            default:
                dialoglayout = inflater.inflate(R.layout.input_question, null);
        }
        TextView title = (TextView) dialoglayout.findViewById(R.id.question);
        title.setText(question.title);
        this.setView(dialoglayout);


        setPositiveButton(R.string.submit, null);
        final AlertDialog dialog = create();
        dialog.show();
        dialog.setCancelable(true);

        Button button = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                boolean valid = true;
                List<String> answer = new ArrayList<String>();
                switch (question.question_type) {
                    case 1:
                        EditText input = (EditText) dialoglayout.findViewById(R.id.answer);
                        String value = input.getText().toString();
                        if(value.isEmpty()){
                            valid = false;
                        }else{
                            answer.add(value);
                        }
                        break;
                    case 2:
                    case 3:
                        ListView single_answers_view = (ListView) dialoglayout.findViewById(R.id.options);
                        answer = ((QuestionAdapter) single_answers_view.getAdapter()).getSelectedItems();
                        if(answer.isEmpty())
                            valid = false;
                        break;
                }
                if(!valid)
                    return;
                EventBus.getDefault().post(new ReplyEvent(question, answer));
                dialog.dismiss();
            }
        });
    }

}