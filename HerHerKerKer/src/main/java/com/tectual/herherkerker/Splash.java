package com.tectual.herherkerker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

/**
 * Created by arash on 30/01/2014.
 */
public class Splash extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        /* ==== NEXT VERSION
        VideoView myVideoView = (VideoView)findViewById(R.id.video);
        Uri video = Uri.parse("android.resource://" + getPackageName() + "/"  + R.raw.intro);
        myVideoView.setVideoURI(video);
        myVideoView.requestFocus();
        myVideoView.start();
        myVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });
        */

        final Typeface faTypeFace = Typeface.createFromAsset(getAssets(), "beiroot.ttf");
        ((TextView) findViewById(R.id.splash)).setTypeface(faTypeFace);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent intent = new Intent(Splash.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, getResources().getInteger(R.integer.splash_page));

    }

}
