package com.example.androidlabs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;
import android.graphics.Bitmap;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    ImageButton mImageButton;
    Button chatButton;
    public static final String ACTIVITY_NAME = "PROFILEACTIVITY";
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent dataFromPreviousPage = getIntent();
        String whatUserTyped = dataFromPreviousPage.getStringExtra("ReservedEmail");
       // double doublevalue=dataFromPreviousPage.getDoubleExtra("doublevalue",0);
        //Log.d("doublevalue-test", Double.toString(doublevalue) );
        EditText emailAddress=findViewById(R.id.enteredEmail);

        emailAddress.setText(whatUserTyped);
        mImageButton=findViewById(R.id.imagePicture);
        mImageButton.setOnClickListener(e->{dispatchTakePictureIntent();});
        chatButton=findViewById(R.id.goToChat);
        if(chatButton != null)
        chatButton.setOnClickListener(v -> {
                Intent goToChatRoom = new Intent(ProfileActivity.this, ChatRoomActivity.class);
                startActivity(goToChatRoom);
            });

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
        super.onActivityResult(requestCode, resultCode, data);
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
