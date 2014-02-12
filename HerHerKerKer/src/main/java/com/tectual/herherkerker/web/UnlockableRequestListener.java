package com.tectual.herherkerker.web;

import android.app.Activity;
import android.content.DialogInterface;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.tectual.herherkerker.Jokes;
import com.tectual.herherkerker.QuestionBuilder;
import com.tectual.herherkerker.R;
import com.tectual.herherkerker.web.data.JsonJokes;
import com.tectual.herherkerker.web.data.JsonQuestion;
import com.tectual.herherkerker.web.data.JsonQuestions;

/**
 * Created by arash on 7/02/2014.
 */
public class UnlockableRequestListener implements RequestListener<JsonQuestions> {

    private Activity activity;

    public UnlockableRequestListener(Activity a){
        activity = a;
    }

    @Override
    public void onRequestFailure(SpiceException spiceException) {
        //update your UI
        SpiceException ex = spiceException;
    }

    @Override
    public void onRequestSuccess(final JsonQuestions jsonQuestions) {
        new QuestionBuilder( activity, jsonQuestions);
    }
}
