package com.example.smartlist;

import java.io.UnsupportedEncodingException;

import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;

public class UpdateProdTitle extends FragmentActivity {
	
	public static final String USER_PREFS = "UserPrefs";
	public static final String SALE_ID = "listing_for_sale_id";
	public static final String OBO_IND = "obo_ind";
	public static final String P_RADIUS = "radius";
	//public static final String P_LANG = "lang";
	public static final String P_CURRENCY = "currency";
	public static final String FEE = "fee";
	public static final String DESCR = "description";
	public static final String TITLE = "title";
	public static final String CATEG = "category";
	public static final String AUTHORIZED = "authenticated";
	private SharedPreferences prefs;
	private SharedPreferences.Editor prefsEditor;
	private PersistentCookieStore myCookieStore;
	
	/*Default Method which will run first*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_product);  /*Retrieving the contents of the layout update_prod*/
        
        retrieveDetails();
    }
    
    public void clickUpdateProd(View v) {
        
        /*Category Spinner is declared*/
        Spinner categoryOption = (Spinner) findViewById(R.id.categoryspinner);
        
        /* Edit Textboxes are declared */
		EditText prodTitle = (EditText) findViewById(R.id.upprodtitle);
		EditText prodDesc = (EditText) findViewById(R.id.upproddescrip);
		EditText expPrice = (EditText) findViewById(R.id.upexpPrice);

		/* Calling a method based on the input */
		this.UpdateProduct(categoryOption.getSelectedItem().toString(),
				prodTitle.getText().toString(), prodDesc.getText().toString(),
				expPrice.getText().toString());
        
    }


    // Activity Lifecycle Overrides
 	/*
 	 * Called when the Activity is no longer visible at all. Stop updates and
 	 * disconnect.
 	 */
 	@Override
 	public void onStop() {
 		super.onStop();
 	}

 	/*
 	 * Called when the Activity is going into the background. Parts of the UI
 	 * may be visible, but the Activity is inactive.
 	 */
 	@Override
 	public void onPause() {
 		super.onPause();
 	}

 	/*
 	 * Called when the Activity is restarted, even before it becomes visible.
 	 */
 	@Override
 	public void onStart() {
 		super.onStart();

 		/*
 		 * Connect the client. Don't re-start any requests here; instead, wait
 		 * for onResume()
 		 */

 	}

 	/*
 	 * Called when the system detects that this Activity is now visible.
 	 */
 	@Override
 	public void onResume() {
 		super.onResume();
 		
 		retrieveDetails();
 	}

 	// LifeCycle end
 	
 	public void retrieveDetails() {
		
		myCookieStore = new PersistentCookieStore(this);
		prefs = getSharedPreferences(USER_PREFS, MODE_PRIVATE);
		prefsEditor = prefs.edit();
		
		/*Category Spinner is declared*/
        Spinner categoryOption = (Spinner) findViewById(R.id.categoryspinner);
        
        /* Edit Textboxes are declared */
		EditText prodTitle = (EditText) findViewById(R.id.upprodtitle);
		EditText prodDesc = (EditText) findViewById(R.id.upproddescrip);
		EditText expPrice = (EditText) findViewById(R.id.upexpPrice);
		
		// Getting the value of Category and sending it to Layout Spinner 
				String defCateg = prefs.getString(CATEG, "VEHICLES");
				ArrayAdapter myAdap1 = (ArrayAdapter) categoryOption.getAdapter();
				int spinnerPosition1 = myAdap1.getPosition(defCateg);
				categoryOption.setSelection(spinnerPosition1);
		
		prodTitle.setText(prefs.getString(TITLE, "")); // Sending the value of Product Title to the Layout Textbox
		
		prodDesc.setText(prefs.getString(DESCR, "")); // Sending the value of Product Description to the Layout Textbox
		
		// Getting the value of Expected Price and sending it to Layout Textbox
		String expectedPrice = String.valueOf(prefs.getInt(FEE, 0));
		expPrice.setText(expectedPrice);
		
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
        	if(!prefs.getBoolean(AUTHORIZED,false)){
        		startActivity(new Intent(UpdateProdTitle.this, MainLogon.class));
        	}else{
        		startActivity(new Intent(UpdateProdTitle.this, AddImageToProd.class));
        	}
            break;
        case R.id.action_prod_setting:
        	if(!prefs.getBoolean(AUTHORIZED,false)){
        		startActivity(new Intent(UpdateProdTitle.this, MainLogon.class));
        	}else{
        		startActivity(new Intent(UpdateProdTitle.this, UpdateProfile.class));
        	}
            break;
        case R.id.action_go_home:
        	if(!prefs.getBoolean(AUTHORIZED,false)){
        		startActivity(new Intent(UpdateProdTitle.this, MainLogon.class));
        	}else{
        		startActivity(new Intent(UpdateProdTitle.this, SmartlisterHome.class));
        		finish();
        	}
            break;
        default:
            return super.onOptionsItemSelected(item);
        }
        return true;
    }
    
    private void UpdateProduct(String category, String title,
			String description, String exprice) {
		if (!prefs.getBoolean(AUTHORIZED, false)) {
			startActivity(new Intent(UpdateProdTitle.this, MainLogon.class));
			return;
		}
		int fee;
		try{
			fee = (int)Double.parseDouble(exprice);
		}catch(Error e){
			fee = 0;
		}
		try {
			JSONObject jsonParams = new JSONObject();
			JSONObject jsonLocParams = new JSONObject();
			jsonParams.put(SALE_ID, this.prefs.getInt(SALE_ID, 1));
			jsonParams.put(CATEG, category.toLowerCase());
			jsonParams.put(TITLE, title);
			jsonParams.put(DESCR, description);
			jsonParams.put(FEE, fee);
			jsonParams.put(P_RADIUS, this.prefs.getInt(P_RADIUS, 50));
			//jsonParams.put(P_LANG, this.prefs.getString(P_LANG, "en").toLowerCase());
			jsonParams.put(P_CURRENCY, this.prefs.getString(P_CURRENCY, "USD").toLowerCase());
			jsonParams.put(OBO_IND, false);// defaulting for now

			StringEntity entity = new StringEntity(jsonParams.toString());
			AsyncHttpClient client = new AsyncHttpClient();
			client.setCookieStore(myCookieStore);
			client.put(getApplicationContext(),
					"http://www.marcstreeter.com/sl/updatelisting", entity,
					"application/json", new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(String response) {
							finish();
						}
						@Override
						public void onFailure(int statusCode,
								org.apache.http.Header[] headers,
								byte[] responseBody, Throwable error) {
							try {
								String response = new String(responseBody,"UTF-8");
								Log.v("RESPONSE",
										"code["
												+ Integer.valueOf(statusCode)
														.toString()
												+ "] headers:"
												+ headers.toString() + " body:"
												+ response);
								
								Toast.makeText(UpdateProdTitle.this, "Product Update Failed. Try Again.!", Toast.LENGTH_SHORT).show();

								//startActivity(new Intent(UpdateProdTitle.this, MainLogon.class));
								//finish();
							} catch (UnsupportedEncodingException e) {
								// TODO Auto-generated catch block
								Log.v("ERROR", e.toString());
								Log.v("ERROR-MESSAGE", e.getMessage());
							}
						}
					});
		} catch (JSONException e) {
			Log.v("CAUGHT", e.toString());
			Log.v("CAUGHT", e.getMessage());

		} catch (UnsupportedEncodingException e) {
			Log.v("CAUGHT", e.toString());
			Log.v("CAUGHT", e.getMessage());
		}
	}

}
