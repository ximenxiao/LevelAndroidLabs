package com.example.androidlabs;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
    //Message msg;
    ListView theList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        //You only need 2 lines in onCreate to actually display data:
        theList = findViewById(R.id.theList);

        MyDatabaseOpenHelper dbOpener = new MyDatabaseOpenHelper(this);
        SQLiteDatabase db = dbOpener.getWritableDatabase();

        String [] columns = {MyDatabaseOpenHelper.COL_ID, MyDatabaseOpenHelper.COL_NAME,MyDatabaseOpenHelper.COL_ISSENT};
        Cursor results = db.query(false, MyDatabaseOpenHelper.TABLE_NAME, columns, null, null, null, null, null, null);
        printCursor(results);
        //find the column indices:
        int nameColIndex = results.getColumnIndex(MyDatabaseOpenHelper.COL_NAME);
        int sentColIndex = results.getColumnIndex(MyDatabaseOpenHelper.COL_ISSENT);
        int idColIndex = results.getColumnIndex(MyDatabaseOpenHelper.COL_ID);

        //iterate over the results, return true if there is a next item:
        while(results.moveToNext())
        {
            String name = results.getString(nameColIndex);
            //String email = results.getString(emailColumnIndex);
            boolean isSent=Boolean.valueOf(results.getString(sentColIndex));
            long id = results.getLong(idColIndex);
            //add the new Contact to the array list:
            objects.add(new Message(name, isSent, id));
        }




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
            newRowValues.put(MyDatabaseOpenHelper.COL_ISSENT, "true");


            long newId = db.insert(MyDatabaseOpenHelper.TABLE_NAME, null, newRowValues);

            //now you have the newId, you can create the Message object
            Message newMessage = new Message(content,true, newId);
            objects.add(newMessage);
            myAdapter.notifyDataSetChanged(); //update yourself
            enteredText.setText("");
            //printCursor(results);
            // theList.setSelection(objects.size());

        });

        receiveButton = findViewById(R.id.receive);
        receiveButton.setOnClickListener( clik ->
        {
            content=enteredText.getText().toString();


            //add to the database and get the new ID
            ContentValues newRowValues = new ContentValues();
            //put string name in the NAME column:
            newRowValues.put(MyDatabaseOpenHelper.COL_NAME, content);
            newRowValues.put(MyDatabaseOpenHelper.COL_ISSENT, "false");

            //msg=new Message(content, 1);
            long newId = db.insert(MyDatabaseOpenHelper.TABLE_NAME, null, newRowValues);

            //now you have the newId, you can create the Message object
            Message newMessage = new Message(content,false, newId);
            objects.add(newMessage);
            myAdapter.notifyDataSetChanged(); //update yourself
            enteredText.setText("");
           // printCursor(results);
            // theList.setSelection(objects.size());

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


                if (getItem(p).isSent() ) {
                    thisRow = inflater.inflate(R.layout.activity_send_avatar, null);

                } else {
                    thisRow = getLayoutInflater().inflate(R.layout.activity_receive_girl, null);

                }


            if (getItem(p).isSent()) {
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
    private void printCursor(Cursor c){
        if(c.getCount()!=0){
            Log.e("DB Version : ", String.valueOf(MyDatabaseOpenHelper.VERSION_NUM));
            Log.e("Number of columns : ", String.valueOf(c.getColumnCount()));
            Log.e("Number of Results : ", String.valueOf(c.getCount()));
            Log.e("Cursors : ", DatabaseUtils.dumpCursorToString(c));


          for(int i=0;i<3;i++) {
            Log.e("Column name are",c.getColumnName(i));
          }}




     /* int numOfColumn=c.getColumnCount();
      String[] names=c.getColumnNames();
      int results=c.getCount();


        //find the column indices:
        int nameColIndex = c.getColumnIndex(MyDatabaseOpenHelper.COL_NAME);
        int sentColIndex = c.getColumnIndex(MyDatabaseOpenHelper.COL_ISSENT);
        int idColIndex = c.getColumnIndex(MyDatabaseOpenHelper.COL_ID);

        //iterate over the results, return true if there is a next item:
        while(c.moveToNext())
        {
            String name = c.getString(nameColIndex);
            //String email = results.getString(emailColumnIndex);
            boolean isSent=Boolean.valueOf(c.getString(sentColIndex));
            long id = c.getLong(idColIndex);

            //add the new Contact to the array list:
            objects.add(new Message(name, isSent, id));
        }


    */
    }

}
