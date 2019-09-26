package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main_linear);
       //setContentView(R.layout.activity_main_relative);
        //setContentView(R.layout.activity_main_grid);
        setContentView(R.layout.activity_email);

        if(loginButton != null)
            loginButton.setOnClickListener(v -> {
                Intent goToPage2 = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(goToPage2);
            });



    }

    EditText editText = findViewById(R.id.emailText);
    SharedPreferences prefs = getSharedPreferences("FileName", MODE_PRIVATE);
    Button loginButton = findViewById(R.id.loginButton);
    @Override
    protected void onPause() {
        super.onPause();
      //  EditText editText = findViewById(R.id.emailText);
        //SharedPreferences prefs = getSharedPreferences("FileName", MODE_PRIVATE);
        String previous = prefs.getString("ReserveEmail", "Default Value");
        editText.setText(previous);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View e) {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("ReserveEmail", editText.getText().toString());
                editor.commit();
            }
        });

    }
}
