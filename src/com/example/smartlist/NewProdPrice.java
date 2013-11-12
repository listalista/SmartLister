package com.example.smartlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;

public class NewProdPrice extends Activity implements OnClickListener {
	
	/*Default Method which will run first*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_list_create_2);  /*Retrieving the contents of the layout new_list_create_2*/
        
        /*Quality Spinner is declared*/
        Spinner pqs = (Spinner) findViewById(R.id.qualityspinner);
        
        /*Location Spinner is declared*/
        Spinner pls = (Spinner) findViewById(R.id.locationspinner);
    
        /*Next button in New Product Creation 2 is declared for onClickListener*/
        Button npn2 = (Button) findViewById(R.id.newprodnext2);
        npn2.setOnClickListener(this);
    }


    /*Executing the onClick listener method when a button is clicked*/
    @Override
    public void onClick(View v) {	
		if(v.getId() == R.id.newprodnext2) {  /*Calling the new_prod_image when Next is clicked*/
			startActivity(new Intent(NewProdPrice.this, NewProdImage.class)); /*New Intent is called*/
		}
    }
}
