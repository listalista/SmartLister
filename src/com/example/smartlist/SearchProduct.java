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
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;

public class SearchProduct extends FragmentActivity {
	
	public static final String USER_PREFS = "UserPrefs";
	public static final String P_LATITUDE = "lat";
	public static final String P_LONGITUDE = "lon";
	public static final String LOCATION = "location";
	public static final String P_RADIUS = "dist";
	public static final String CRITERIA = "criteria";
	public static final String FEEMIN = "fee_min";
	public static final String FEEMAX = "fee_max";
	public static final String KEYWORDS = "key_words";
	public static final String CATEG = "category";
	public static final String AUTHORIZED = "authenticated";
	private SharedPreferences prefs;
	private SharedPreferences.Editor prefsEditor;
	private PersistentCookieStore myCookieStore;
	
	/*Default Method which will run first*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buyer_search);  /*Retrieving the contents of the layout buyer_search*/
    

    }

    public void clickSearchProd(View v) {
		/* Category Spinner is declared */
		Spinner categoryOption = (Spinner) findViewById(R.id.categoryspinner);

		/* Edit Textboxes are declared */
		EditText keyWord = (EditText) findViewById(R.id.keyword);
		EditText minPrice = (EditText) findViewById(R.id.exppricefrom);
		EditText maxPrice = (EditText) findViewById(R.id.exppriceto);

		/* Calling a method based on the input */
		this.SearchProducts(categoryOption.getSelectedItem().toString(),
				keyWord.getText().toString(), minPrice.getText().toString(),
				maxPrice.getText().toString());

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
		(new CurrentLocation(this, this)).execute();
		myCookieStore = new PersistentCookieStore(this);
		prefs = getSharedPreferences(USER_PREFS, MODE_PRIVATE);
		prefsEditor = prefs.edit();
	}

	// LifeCycle end
    
	private void SearchProducts(String category, String keywords,
			String minprices, String maxprices) {
		int minamount, maxamount;
		if(minprices.equals("")) {
			minamount = -1;
		}else {
			minamount = Integer.parseInt(minprices);
		}
<<<<<<< HEAD
=======
		else {
			minamount = (int)Double.parseDouble(minprices);
		}
>>>>>>> bfd3cfc4e5c16da985e236287951e0ed849f538c
		if(maxprices.equals("")) {
			maxamount = -1;
		}else {
			maxamount = Integer.parseInt(maxprices);
		}
		try {
			JSONObject jsonParams = new JSONObject();
			JSONObject jsonCritParams = new JSONObject();
			jsonParams.put("listings_per_page",50);
    		jsonParams.put("current_page",1);
    		jsonParams.put(P_LATITUDE, this.prefs.getFloat(P_LATITUDE,(float) 0.0));
    		jsonParams.put(P_LONGITUDE, this.prefs.getFloat(P_LONGITUDE,(float) 0.0));
			jsonParams.put(P_RADIUS, this.prefs.getInt(P_RADIUS, 50));
			jsonCritParams.put(CATEG, category.toLowerCase());
			jsonCritParams.put(FEEMIN, minamount);
			jsonCritParams.put(FEEMAX, maxamount);
			jsonCritParams.put(KEYWORDS, keywords);
			jsonParams.put(CRITERIA, jsonCritParams);
			

			StringEntity entity = new StringEntity(jsonParams.toString());
			AsyncHttpClient client = new AsyncHttpClient();
			client.setCookieStore(myCookieStore);
			client.put(getApplicationContext(),
					"http://www.marcstreeter.com/sl/listings", entity,
					"application/json", new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(String response) {
							startActivity(new Intent(SearchProduct.this, Listings.class));
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
								
								Toast.makeText(SearchProduct.this, "Search Failed. Try Again Later.!", Toast.LENGTH_SHORT).show();

								//startActivity(new Intent(NewProd.this, MainLogon.class));
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

