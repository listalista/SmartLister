package com.example.smartlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;

public class RegisterUser extends Activity implements OnClickListener {
	
	/*Default Method which will run first*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_user_register);  /*Retrieving the contents of the layout new_user_register*/
        
        /*Create button in New User Registration is declared for onClickListener*/
        Button nur = (Button) findViewById(R.id.createuser);
        nur.setOnClickListener(this);
    }

    
    /*Executing the onClick listener method when a button is clicked*/
    @Override
    public void onClick(View v) {	
    	if(v.getId() == R.id.createuser) {  /*Calling the home_page when Create is clicked*/
    		startActivity(new Intent(RegisterUser.this, SmartlisterHome.class)); /*New Intent is called*/
    	}
    }
}
