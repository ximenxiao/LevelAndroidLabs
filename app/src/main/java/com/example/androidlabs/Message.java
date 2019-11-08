package com.example.androidlabs;

import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Message extends AppCompatActivity {

   // public static final int TYPE_RECE = 0;
    //public static final int TYPE_SEND = 1;
    private boolean isSent;
    private String message;
    //private int type;
    private long dbid;

    public Message(String message, boolean isSent, long dbid){
        this.message=message;
       // this.type=type;
        this.isSent=isSent;
        this.dbid=dbid;
    }

    public String getMessage(){
        return message;
    }

    public boolean isSent() {
        return isSent;
    }

    public long getdbid() {
        return dbid;
    }
}
