package com.example.smartlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;

public class UpdateProdTitle extends Activity implements OnClickListener {
	
	/*Default Method which will run first*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_product);  /*Retrieving the contents of the layout update_prod*/
        
        /*Category Spinner is declared*/
        Spinner spc = (Spinner) findViewById(R.id.categoryspinner);
        
        /*Update button in Update Product is declared for onClickListener*/
        Button upc = (Button) findViewById(R.id.updateprodconfirm);
        upc.setOnClickListener(this);
    }


    /*Executing the onClick listener method when a button is clicked*/
    @Override
    public void onClick(View v) {	
		if(v.getId() == R.id.updateprodconfirm) {  /*Calling the seller_menu when Update is clicked*/
			startActivity(new Intent(UpdateProdTitle.this, SellerMenu.class)); /*New Intent is called*/
			finish();
		}
    }
    
    /* Inflating the Menu options */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.product_options, menu);
        return true;
    }
    
    
    /* Performing action upon selecting a Menu Item */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
        case R.id.action_Prod_cam:
        	startActivity(new Intent(UpdateProdTitle.this, AddImageToProd.class));
            break;
        case R.id.action_prod_setting:
            startActivity(new Intent(UpdateProdTitle.this, UpdateProfile.class));
               break;
        case R.id.action_go_home:
            startActivity(new Intent(UpdateProdTitle.this, SmartlisterHome.class));
            finish();
            break;
        default:
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

}
