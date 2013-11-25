package com.example.smartlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class UpdateProfile extends Activity implements OnClickListener {
	
	/*Default Method which will run first*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_user_profile);  /*Retrieving the contents of the layout update_user_profile*/
        
        /*Create button in New User Registration 2 is declared for onClickListener*/
        Button up = (Button) findViewById(R.id.updateprof);
        up.setOnClickListener(this);
    }

    
    /*Executing the onClick listener method when a button is clicked*/
    @Override
    public void onClick(View v) {	
    	if(v.getId() == R.id.updateprof) {  /*Calling the home_page when Update is clicked*/
    		startActivity(new Intent(UpdateProfile.this, SmartlisterHome.class)); /*New Intent is called*/
    		finish();
    	}
    }

}
