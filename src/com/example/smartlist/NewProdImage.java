package com.example.smartlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class NewProdImage extends Activity implements OnClickListener {
	
	/*Default Method which will run first*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.new_list_create_3);  /*Retrieving the contents of the layout new_list_create_3*/
        
        /*Create button in New Product Creation 3 is declared for onClickListener*/
        //Button npn3 = (Button) findViewById(R.id.newprodcreate);
        //npn3.setOnClickListener(this);
    }


    /*Executing the onClick listener method when a button is clicked*/
    @Override
    public void onClick(View v) {	
		//if(v.getId() == R.id.newprodcreate) {  /*Calling the seller_menu when Next is clicked*/
		//	startActivity(new Intent(NewProdImage.this, SellerMenu.class)); /*New Intent is called*/
		//}
    }
}