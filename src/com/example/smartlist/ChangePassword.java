package com.example.smartlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ChangePassword extends Activity implements OnClickListener {
	
	/*Default Method which will run first*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password);  /*Retrieving the contents of the layout reset_password*/
    
        /*Update button in Change Password is declared for onClickListener*/
        Button sp = (Button) findViewById(R.id.updatepass);
        sp.setOnClickListener(this);
    }


    /*Executing the onClick listener method when a button is clicked*/
    @Override
    public void onClick(View v) {	
		if(v.getId() == R.id.updatepass) {  /*Calling the home_page when Update is clicked*/
			startActivity(new Intent(ChangePassword.this, SmartlisterHome.class)); /*New Intent is called*/
			finish();
		}
    }

}
