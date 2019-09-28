package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button loginButton;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main_linear);
       //setContentView(R.layout.activity_main_relative);
        //setContentView(R.layout.activity_main_grid);
        setContentView(R.layout.activity_email);

        editText=findViewById(R.id.emailText);
        prefs = getSharedPreferences("FileName", MODE_PRIVATE);
        loginButton = findViewById(R.id.loginButton);
        String previous = prefs.getString("ReserveEmail", "Default Value");
        editText.setText(previous);
        //Log.d("MainActivity",previous);

        if(loginButton != null)
            loginButton.setOnClickListener(v -> {
                Intent goToPage2 = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(goToPage2);
            });




    }


    @Override
    protected void onPause() {
        super.onPause();
      //  EditText editText = findViewById(R.id.emailText);
        //SharedPreferences prefs = getSharedPreferences("FileName", MODE_PRIVATE);


                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("ReserveEmail", editText.getText().toString());
                editor.commit();
            }



}
