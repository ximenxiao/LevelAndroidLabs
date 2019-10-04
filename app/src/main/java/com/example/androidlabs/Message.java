package com.example.androidlabs;

import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Message extends AppCompatActivity {

    public static final int TYPE_RECE = 0;
    public static final int TYPE_SEND = 1;

    private String message;
    public int type;

    public Message(String message, int type){
        this.message=message;
        this.type=type;
    }

    public String getMessage(){
        return message;
    }

    public int getType(){
        return type;
    }



}
