package com.example.androidlabs;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;



public class ChatRoomActivity extends AppCompatActivity  {
    ArrayList<Message> objects = new ArrayList<Message>( );
    BaseAdapter myAdapter;
    Button sendButton;
    Button receiveButton;
    EditText enteredText;
    String content;
    Message msg;
    ListView theList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        //You only need 2 lines in onCreate to actually display data:
        theList = findViewById(R.id.theList);

        MyDatabaseOpenHelper dbOpener = new MyDatabaseOpenHelper(this);
        SQLiteDatabase db = dbOpener.getWritableDatabase();

        theList.setAdapter( myAdapter = new MyListAdapter() );
        theList.setOnItemClickListener( ( lv, vw, pos, id) ->{
            Toast.makeText( ChatRoomActivity.this,
                    "You clicked on:" + pos, Toast.LENGTH_SHORT).show();

        } );
        //chatroom


        sendButton = findViewById(R.id.send);
        enteredText=findViewById(R.id.messageTyped);

        sendButton.setOnClickListener( clik ->
        {

            content=enteredText.getText().toString();

            //add to the database and get the new ID
            ContentValues newRowValues = new ContentValues();
            //put string name in the NAME column:
            newRowValues.put(MyDatabaseOpenHelper.COL_NAME, content);
            //msg=new Message(content, 1);
            //objects.add(msg);
            long newId = db.insert(MyDatabaseOpenHelper.TABLE_NAME, null, newRowValues);
            String [] columns = {MyDatabaseOpenHelper.COL_ID, MyDatabaseOpenHelper.COL_EMAIL, MyDatabaseOpenHelper.COL_NAME};
            Cursor results = db.query(false, MyDatabaseOpenHelper.TABLE_NAME, columns, null, null, null, null, null, null);

            //now you have the newId, you can create the Contact object
          //  Message newMessage = new Message(content,1, newId);
            myAdapter.notifyDataSetChanged(); //update yourself
            enteredText.setText("");
            // theList.setSelection(objects.size());

        });

        receiveButton = findViewById(R.id.receive);
        receiveButton.setOnClickListener( clik ->
        {
           // enteredText=findViewById(R.id.messageTyped);
           // enteredText=findViewById(R.id.messageTyped);
            content=enteredText.getText().toString();
            ContentValues newRowValues = new ContentValues();
            //put string name in the NAME column:
            newRowValues.put(MyDatabaseOpenHelper.COL_NAME, content);
            long newId = db.insert(MyDatabaseOpenHelper.TABLE_NAME, null, newRowValues);

            msg=new Message(content, Message.TYPE_RECE, newId);
            objects.add(msg);
            enteredText.setText("");
            myAdapter.notifyDataSetChanged(); //update yourself
            //theList.setSelection(objects.size());

        });

    }


    //Need to add 4 functions here:
    private class MyListAdapter extends BaseAdapter {

       // public MyListAdapter(Context context, int )
        public int getCount() {
            return objects.size();
        } //This function tells how many objects to show

        public Message getItem(int position) {
            return objects.get(position);
        }  //This returns the Message object at position p

        public long getItemId(int p) {
            return p;
        } //This returns the database id of the item at position p
        //question 4
      /*  public View getView(int p, View recycled, ViewGroup parent)
        {
            View thisRow = recycled;

            if(recycled == null)
                thisRow = getLayoutInflater().inflate(R.layout.table_row_layout, null);

            TextView itemText = thisRow.findViewById(R.id.itemField  );
            itemText.setText( "Item " + p );

            TextView numberText = thisRow.findViewById(R.id.numberField);
            numberText.setText("Sub Item " + p);
            return thisRow;
        }*/

        public View getView(int p, View recycled, ViewGroup parent) {
            View thisRow = null;//recycled;
            LayoutInflater inflater = getLayoutInflater();

            if (thisRow == null) {
                if (getItem(p).getType() == 1) {
                    thisRow = inflater.inflate(R.layout.activity_send_avatar, null);

                } else {
                    thisRow = getLayoutInflater().inflate(R.layout.activity_receive_girl, null);

                }

            }
            if (getItem(p).getType() == 1) {
                TextView itemText = thisRow.findViewById(R.id.sendMessageText);
                itemText.setText(getItem(p).getMessage());
            } else {
                //thisRow = getLayoutInflater().inflate(R.layout.activity_receive_girl, null);
                TextView itemText = thisRow.findViewById(R.id.receiveMessageText);
                itemText.setText(getItem(p).getMessage());
            }
            return thisRow;


        }
    }

}
