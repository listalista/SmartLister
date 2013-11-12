package com.example.smartlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class DeleteConfirm extends Activity implements OnClickListener {
	
	/*Default Method which will run first*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_product_confirm);  /*Retrieving the contents of the layout delete_product_confirm*/
    
        /*Delete button in Delete Confirmation page is declared for onClickListener*/
        Button dp = (Button) findViewById(R.id.deleteprod);
        dp.setOnClickListener(this);
        
        /*Cancel button in Delete Confirmation page is declared for onClickListener*/
        Button cdp = (Button) findViewById(R.id.canceldeleteprod);
        cdp.setOnClickListener(this);
    }


    /*Executing the onClick listener method when a button is clicked*/
    @Override
    public void onClick(View v) {	
		if(v.getId() == R.id.deleteprod) {  /*Calling the seller_menu when Submit is clicked*/
			startActivity(new Intent(DeleteConfirm.this, SellerMenu.class)); /*New Intent is called*/
		}
		else if(v.getId() == R.id.canceldeleteprod) {  /*Calling the seller_menu when Submit is clicked*/
			startActivity(new Intent(DeleteConfirm.this, SellerMenu.class)); /*New Intent is called*/
		}
    }

}
