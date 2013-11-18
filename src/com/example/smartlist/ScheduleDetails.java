package com.example.smartlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
			/*Track Location Code user Construction*/
		}
		else if(v.getId() == R.id.cancelsch) {  /*Calling the seller_menu when Submit is clicked*/
			startActivity(new Intent(ScheduleDetails.this, ScheduleList.class)); /*New Intent is called*/
		}
    }

}