package com.example.androidlabs;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
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
    MyDatabaseOpenHelper dbOpener;
    SQLiteDatabase db;
    //Message msg;
    ListView theList;
    public static final String ITEM_SELECTED = "ITEM";
    public static final String ITEM_POSITION = "POSITION";
    public static final String ITEM_SENT = "SENT";
    public static final String ITEM_ID = "ID";
    public static final int EMPTY_ACTIVITY = 345;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        boolean isTablet = findViewById(R.id.fragmentLocation) != null; //check if the FrameLayout is loaded

        //You only need 2 lines in onCreate to actually display data:
        theList = findViewById(R.id.theList);

        dbOpener = new MyDatabaseOpenHelper(this);
        db = dbOpener.getWritableDatabase();

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



        myAdapter = new MyListAdapter();
        theList.setAdapter( myAdapter);
        theList.setOnItemClickListener((parent,view,position, id)->{
            Log.d("aaaaaaaaaaaaaa","fffffffffffff");
           int positionClicked = position;

           Message choseOne= objects.get(positionClicked);

            Bundle dataToPass = new Bundle();
            dataToPass.putString(ITEM_SELECTED, choseOne.getMessage() );
            dataToPass.putBoolean(ITEM_SENT, choseOne.isSent());
            dataToPass.putInt(ITEM_POSITION, positionClicked);
            dataToPass.putLong(ITEM_ID, choseOne.getdbid());

            if(isTablet)
            {
                DetailFragment dFragment = new DetailFragment(); //add a DetailFragment
                dFragment.setArguments( dataToPass ); //pass it a bundle for information
                dFragment.setTablet(true);  //tell the fragment if it's running on a tablet or not
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragmentLocation, dFragment) //Add the fragment in FrameLayout
                        .addToBackStack("AnyName") //make the back button undo the transaction
                        .commit(); //actually load the fragment.
            }
            else //isPhone
            {
                Intent nextActivity = new Intent(ChatRoomActivity.this, EmptyActivity.class);
                nextActivity.putExtras(dataToPass); //send data to next activity
                startActivityForResult(nextActivity, EMPTY_ACTIVITY); //make the transition
            }
        });
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


    //This function only gets called on the phone. The tablet never goes to a new activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == EMPTY_ACTIVITY)
        {
            if(resultCode == RESULT_OK) //if you hit the delete button instead of back button
            {
                long id = data.getLongExtra(ITEM_ID, 0);
                int position=data.getIntExtra(ITEM_POSITION, 0);
                //deleteMessageId((int)id);

                Log.d("aaaaaaaaaaaaaaa", String.valueOf(id));
                deleteMessageId(position);
            }
        }
    }

    public void deleteMessageId(int position)
    {
        Log.i("Delete this message:" , " id="+position);
        long id = objects.get(position).getdbid();
        objects.remove(position);

        int numDeleted = db.delete(MyDatabaseOpenHelper.TABLE_NAME, MyDatabaseOpenHelper.COL_ID + "=?", new String[] {Long.toString(id)});
        myAdapter.notifyDataSetChanged();
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
