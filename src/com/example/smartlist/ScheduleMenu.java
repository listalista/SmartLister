package com.example.smartlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ScheduleMenu extends Activity implements OnClickListener  {
	
	/*Default Method which will run first*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_menu);  /*Retrieving the contents of the layout seller_menu*/
    
        /*New Schedule button in Schedule Menu is declared for onClickListener*/
        Button cns = (Button) findViewById(R.id.createnewsch);
        cns.setOnClickListener(this);
        
        /*View Existing button in Schedule Menu is declared for onClickListener*/
        Button ves = (Button) findViewById(R.id.viewexistingsch);
        ves.setOnClickListener(this);
        
    }


    /*Executing the onClick listener method when a button is clicked*/
    @Override
    public void onClick(View v) {	
		if(v.getId() == R.id.createnewsch) {  /*Calling the new_schedule when New Schedule is clicked*/
			startActivity(new Intent(ScheduleMenu.this, NewSchedule.class)); /*New Intent is called*/
		}
		else if(v.getId() == R.id.viewexistingsch) {  /*Calling the schedule_list when View Existing Schedule is clicked*/
			startActivity(new Intent(ScheduleMenu.this, ScheduleList.class)); /*New Intent is called*/
		}
    }

}
