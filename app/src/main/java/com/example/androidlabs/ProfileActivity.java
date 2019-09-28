package com.example.androidlabs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;
import android.graphics.Bitmap;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {
    SharedPreferences prefs;
    ImageButton mImageButton;
    public static final String ACTIVITY_NAME = "PROFILEACTIVITY";

    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main_linear);
        //setContentView(R.layout.activity_main_relative);
        //setContentView(R.layout.activity_main_grid);
        setContentView(R.layout.activity_second);
        prefs = getSharedPreferences("FileName", MODE_PRIVATE);
        String previous = prefs.getString("ReserveEmail", "Default Value");

        EditText emailAddress=findViewById(R.id.enteredEmail);
        emailAddress.setText(previous);
        mImageButton=findViewById(R.id.imagePicture);
        mImageButton.setOnClickListener(e->{dispatchTakePictureIntent();});
        Log.e("ACTIVITY_NAME","In function:"+"OnCreate");
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageButton.setImageBitmap(imageBitmap);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("ACTIVITY_NAME","In function:"+"OnStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("ACTIVITY_NAME","In function:"+"OnResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("ACTIVITY_NAME","In function:"+"OnPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("ACTIVITY_NAME","In function:"+"OnStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("ACTIVITY_NAME","In function:"+"OnDestroy");
    }
}
