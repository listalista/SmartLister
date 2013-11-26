package com.example.smartlist;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.loopj.android.http.PersistentCookieStore;

public class SelectedProduct extends Activity implements OnClickListener {
	private SharedPreferences prefs;
	private SharedPreferences.Editor prefsEditor;
	public static final String USER_PREFS = "UserPrefs";
	public static final String AUTHORIZED = "authenticated";
	/*Default Method which will run first*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buyer_product_select);  /*Retrieving the contents of the layout buyer_product_select*/
    
        /*Start Chat button in Product Select page is declared for onClickListener*/
        Button dp = (Button) findViewById(R.id.startchat);
        dp.setOnClickListener(this);
        
        /*Cancel button in Product Select page is declared for onClickListener*/
        Button cdp = (Button) findViewById(R.id.cancelprod);
        cdp.setOnClickListener(this);
        
        /* Retrieving the values from Lisings.java and storing it in variables */
        Intent intent = getIntent();

		float lat = intent.getFloatExtra("lat", 0);
		float lon = intent.getFloatExtra("lon", 0);
		String title = intent.getStringExtra("title");
		String description = intent.getStringExtra("description");
		String currency = intent.getStringExtra("currency");
		int fee = intent.getIntExtra("fee", 0);
		String category = intent.getStringExtra("category");
		boolean obo = intent.getBooleanExtra("obo",false);
		String created = intent.getStringExtra("created");
		
		
		/* Declaring the Textview to a variable */
		TextView prodTitle = (TextView) findViewById(R.id.selprodtitle);
		TextView prodDesc = (TextView) findViewById(R.id.selproddesc);
		TextView expPrice = (TextView) findViewById(R.id.selexpprice);
		
		/* Sending the Intent Values to the TextView */
		prodTitle.setText(title);
		prodDesc.setText(description);
		expPrice.setText(fee + " " + currency);	
    }


    /*Executing the onClick listener method when a button is clicked*/
    @Override
    public void onClick(View v) {	
		if(v.getId() == R.id.startchat) {  /*Calling the seller_menu when Submit is clicked*/
			startActivity(new Intent(SelectedProduct.this, ChatApplication.class)); /*New Intent is called*/
		}
		else if(v.getId() == R.id.cancelprod) {  /*Calling the seller_menu when Submit is clicked*/
			startActivity(new Intent(SelectedProduct.this, RefinedProdList.class)); /*New Intent is called*/
		}
    }
    @Override 
    public void onResume(){
		prefs = getSharedPreferences(USER_PREFS, MODE_PRIVATE);
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
        	if(!prefs.getBoolean(AUTHORIZED,false)){
        		startActivity(new Intent(SelectedProduct.this, MainLogon.class));
        	}else{
        		startActivity(new Intent(SelectedProduct.this, SmartlisterHome.class));
        	}
            break;
        default:
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

}