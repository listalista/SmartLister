package com.example.smartlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ForgotPassword extends Activity implements OnClickListener {
	
	/*Default Method which will run first*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);  /*Retrieving the contents of the layout forgot_password*/
    
        /*Submit button in Password Help is declared for onClickListener*/
        Button sp = (Button) findViewById(R.id.submitsec);
        sp.setOnClickListener(this);
    }


    /*Executing the onClick listener method when a button is clicked*/
    @Override
    public void onClick(View v) {	
		if(v.getId() == R.id.submitsec) {  /*Calling the reset_password when Submit is clicked*/
			startActivity(new Intent(ForgotPassword.this, ChangePassword.class)); /*New Intent is called*/
		}
    }

}
