package com.example.smartlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;

public class NewProdTitle extends Activity implements OnClickListener {
	
	/*Default Method which will run first*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_list_create_1);  /*Retrieving the contents of the layout new_list_create_1*/
        
        /*Category Spinner is declared*/
        Spinner spc = (Spinner) findViewById(R.id.categoryspinner);
        
        /*Next button in New Product Creation 1 is declared for onClickListener*/
        Button npn1 = (Button) findViewById(R.id.newprodnext1);
        npn1.setOnClickListener(this);
    }


    /*Executing the onClick listener method when a button is clicked*/
    @Override
    public void onClick(View v) {	
		if(v.getId() == R.id.newprodnext1) {  /*Calling the new_list_create_2 when Next is clicked*/
			startActivity(new Intent(NewProdTitle.this, NewProdPrice.class)); /*New Intent is called*/
		}
    }

}
