package com.tectual.herherkerker;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.GpsStatus;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EventListener;

/**
 * Created by arash on 1/02/2014.
 */
public class ProfileActivity extends Activity implements View.OnClickListener {

    private Integer GALLERY_ACTION = 1;
    private Integer CROP_ACTION = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        setTitle(R.string.profile);

        ((ImageButton) findViewById(R.id.attach_profile_image))
                .setOnClickListener(new View.OnClickListener() {
                    public void onClick(View arg0) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, getString(R.string.attach_profile_image)), GALLERY_ACTION);
                    }
                });
        Button cancel_button = (Button) findViewById(R.id.cancel);
        cancel_button.setOnClickListener(this);
        Button save_button = (Button) findViewById(R.id.save_profile);
        save_button.setOnClickListener(this);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode==GALLERY_ACTION){
                Uri selectedImageUri = data.getData();
                doCrop(selectedImageUri);
            }else if(requestCode==CROP_ACTION){
                if (data != null) {
                    Bundle extras = data.getExtras();
                    Bitmap croppedImage = extras.getParcelable("data");
                    ((ImageView) findViewById(R.id.profile_image)).setImageBitmap(croppedImage);
                }
            }
        }
    }

    private void doCrop(Uri image) {
        try {
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            cropIntent.setDataAndType(image, "image/*");
            cropIntent.putExtra("crop", "true");
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            cropIntent.putExtra("outputX", 400);
            cropIntent.putExtra("outputY", 400);
            cropIntent.putExtra("return-data", true);
            startActivityForResult(cropIntent, CROP_ACTION);
        }catch (ActivityNotFoundException ex) {
            String errorMessage = getString(R.string.error_no_crop);
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.cancel){
            onBackPressed();
        }else if(v.getId() == R.id.save_profile){
            onBackPressed();
        }
    }

}
