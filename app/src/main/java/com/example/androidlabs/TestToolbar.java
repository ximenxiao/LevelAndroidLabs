package com.example.androidlabs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static android.app.ProgressDialog.show;
import com.google.android.material.snackbar.Snackbar;
public class TestToolbar extends AppCompatActivity {

String message="You clicked on overflow menu";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);

        Toolbar myToolbar = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menuitem, menu);


        /* slide 15 material:  */
       /* MenuItem searchItem = menu.findItem(R.id.choice1);
        SearchView sView = (SearchView) searchItem.getActionView();
        sView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });*/

        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            //what to do when the menu item is selected:
            case R.id.choice4:
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                break;
            case R.id.choice1:
                Toast.makeText(this, "This is the initial message", Toast.LENGTH_LONG).show();
                break;
            case R.id.choice2:
                alertExample();
                break;
            case R.id.choice3:
               Snackbar snackbar=Snackbar.make(findViewById(R.id.choice3), "",Snackbar.LENGTH_SHORT);
               snackbar.setAction( "Go Back?", v->{
                   finish();
          });
               snackbar.show();

                break;
        }
        return true;
    }


    public void alertExample()
    {
        View middle = getLayoutInflater().inflate(R.layout.view_extra_stuff, null);
        //Button btn = (Button)middle.findViewById(R.id.view_button);

        //btn.setOnClickListener( clk -> et.setText("You clicked my button!"));

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setMessage("The Message")
            builder.setPositiveButton("Positive", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EditText et = (EditText)middle.findViewById(R.id.view_edit_text);
                      //  Log.d("111111111111","2222222");
                        message=et.getText().toString();
                        et.setText(message);
                    }
                })
                .setNegativeButton("Negative", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // What to do on Cancel
                    }
                }).setView(middle);

        builder.create().show();
    }






}
