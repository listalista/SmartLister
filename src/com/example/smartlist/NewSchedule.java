package com.example.smartlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class NewSchedule extends Activity implements OnClickListener  {
	
	/*Default Method which will run first*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_schedule);  /*Retrieving the contents of the layout seller_menu*/
    
        /*Search button in Schedule Menu is declared for onClickListener*/
        Button skc = (Button) findViewById(R.id.searchkeycode);
        skc.setOnClickListener(this);
        
    }


    /*Executing the onClick listener method when a button is clicked*/
    @Override
    public void onClick(View v) {	
		if(v.getId() == R.id.searchkeycode) {  /*Calling the create_schedule when New Schedule is clicked*/
			startActivity(new Intent(NewSchedule.this, CreateSchedule.class)); /*New Intent is called*/
		}
    }

}
