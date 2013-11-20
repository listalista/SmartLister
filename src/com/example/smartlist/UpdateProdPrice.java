package com.example.smartlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;

public class UpdateProdPrice extends Activity implements OnClickListener {
	
	/*Default Method which will run first*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_list_prod_2);  /*Retrieving the contents of the layout update_list_prod_2*/
        
        /*Quality Spinner is declared*/
        Spinner pqs = (Spinner) findViewById(R.id.qualityspinner);
        
        /*Location Spinner is declared*/
        Spinner pls = (Spinner) findViewById(R.id.locationspinner);
    
        /*Next button in New Product Creation 2 is declared for onClickListener*/
        Button upn2 = (Button) findViewById(R.id.updateprodconfirm);
        upn2.setOnClickListener(this);
    }


    /*Executing the onClick listener method when a button is clicked*/
    @Override
    public void onClick(View v) {	
		if(v.getId() == R.id.updateprodconfirm) {  /*Calling the update_list_prod_3 when Next is clicked*/
			startActivity(new Intent(UpdateProdPrice.this, SellerMenu.class)); /*New Intent is called*/
		}
    }
}

