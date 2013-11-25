package com.example.smartlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class ChatApplication extends Activity {
	
	/*Default Method which will run first*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_application);  /*Retrieving the contents of the layout chat_application*/
    }
    
    /* Inflating the Menu options */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_logon, menu);
        return true;
    }
    
    
    /* Performing action upon selecting a Menu Item */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
        case R.id.action_new_schedule:
         startActivity(new Intent(ChatApplication.this, CreateSchedule.class));
            break;
        case R.id.action_go_home:
            startActivity(new Intent(ChatApplication.this, SmartlisterHome.class));
               break;
        default:
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

}
