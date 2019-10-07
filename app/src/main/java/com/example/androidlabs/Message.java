package com.example.androidlabs;

import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Message extends AppCompatActivity {

    public static final int TYPE_RECE = 0;
    public static final int TYPE_SEND = 1;

    String message;
    public int type;
    long dbid;

    public Message(String message, int type, long dbid){
        this.message=message;
        this.type=type;
        this.dbid=dbid;
    }

    public String getMessage(){
        return message;
    }

    public int getType(){
        return type;
    }

    public long getdbid() {
        return dbid;
    }
}
