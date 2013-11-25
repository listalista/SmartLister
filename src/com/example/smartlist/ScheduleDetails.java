package com.example.smartlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ScheduleDetails extends Activity implements OnClickListener {
	
	/*Default Method which will run first*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_details);  /*Retrieving the contents of the layout buyer_product_select*/
    
        /*Start Chat button in Product Select page is declared for onClickListener*/
        Button trl = (Button) findViewById(R.id.tracklocation);
        trl.setOnClickListener(this);
        
        /*Cancel button in Product Select page is declared for onClickListener*/
        Button cdp = (Button) findViewById(R.id.cancelsch);
        cdp.setOnClickListener(this);
    }


    /*Executing the onClick listener method when a button is clicked*/
    @Override
    public void onClick(View v) {	
		if(v.getId() == R.id.tracklocation) {  /*Calling the seller_menu when Submit is clicked*/
			startActivity(new Intent(ScheduleDetails.this, TrackLocation.class)); /*New Intent is called*/
		}
		else if(v.getId() == R.id.cancelsch) {  /*Calling the seller_menu when Submit is clicked*/
			startActivity(new Intent(ScheduleDetails.this, ScheduleList.class)); /*New Intent is called*/
		}
    }
    
    /* Inflating the Menu options */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.go_home, menu);
        return true;
    }
    
    
    /* Performing action upon selecting a Menu Item */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
        case R.id.action_go_home:
            startActivity(new Intent(ScheduleDetails.this, SmartlisterHome.class));
            finish();
            break;
        default:
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

}