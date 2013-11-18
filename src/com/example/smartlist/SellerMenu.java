package com.example.smartlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SellerMenu extends Activity implements OnClickListener  {
	
	/*Default Method which will run first*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seller_menu);  /*Retrieving the contents of the layout seller_menu*/
    
        /*List New Product button in Seller Menu is declared for onClickListener*/
        Button sp = (Button) findViewById(R.id.listnewprodbutton);
        sp.setOnClickListener(this);
        
        /*Update Existing button in Seller Menu is declared for onClickListener*/
        Button up = (Button) findViewById(R.id.updateprodbutton);
        up.setOnClickListener(this);
        
        /*Delete Existing button in Seller Menu is declared for onClickListener*/
        Button dp = (Button) findViewById(R.id.deleteprodbutton);
        dp.setOnClickListener(this);
    }


    /*Executing the onClick listener method when a button is clicked*/
    @Override
    public void onClick(View v) {	
		if(v.getId() == R.id.listnewprodbutton) {  /*Calling the new_list_create_1 when List New Product is clicked*/
			startActivity(new Intent(SellerMenu.this, NewProd.class)); /*New Intent is called*/
		}
		else if(v.getId() == R.id.updateprodbutton) {  /*Calling the update_list_view when List New Product is clicked*/
			startActivity(new Intent(SellerMenu.this, UpdateProdView.class)); /*New Intent is called*/
		}
		else if(v.getId() == R.id.deleteprodbutton) {  /*Calling the delete_list_view when List New Product is clicked*/
			startActivity(new Intent(SellerMenu.this, DeleteProdView.class)); /*New Intent is called*/
		}
    }

}
