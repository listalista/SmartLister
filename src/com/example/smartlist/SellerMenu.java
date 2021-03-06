package com.example.smartlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
        
        
    }


    /*Executing the onClick listener method when a button is clicked*/
    @Override
    public void onClick(View v) {	
		if(v.getId() == R.id.listnewprodbutton) {  /*Calling the new_list_create_1 when List New Product is clicked*/
			startActivity(new Intent(SellerMenu.this, NewProd.class)); /*New Intent is called*/
		}
		else if(v.getId() == R.id.updateprodbutton) {  /*Calling the update_list_view when List New Product is clicked*/
			startActivity(new Intent(SellerMenu.this, MyListings.class)); /*New Intent is called*/
		}
    }
    
    /* Inflating the Menu options */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.go_home, menu);
        return true;
    }
    
    
    /* Performing action upon selecting a Menu Item */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
        case R.id.action_go_home:
            startActivity(new Intent(SellerMenu.this, SmartlisterHome.class));
            finish();
            break;
        default:
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

}
