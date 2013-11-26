package com.example.smartlist;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;

import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;

public class NewProd extends FragmentActivity {

	public static final String USER_PREFS = "UserPrefs";
	public static final String P_LATITUDE = "lat";
	public static final String P_LONGITUDE = "lon";
	public static final String LOCATION = "location";
	public static final String LAT_LON_TS = "lat_lon_ts";
	public static final String OBO_IND = "obo_ind";
	public static final String P_RADIUS = "radius";
	public static final String P_LANG = "lang";
	public static final String P_CURRENCY = "currency";
	public static final String FEE = "fee";
	public static final String DESCR = "description";
	public static final String TITLE = "title";
	public static final String CATEG = "category";
	public static final String AUTHORIZED = "authenticated";
	private SharedPreferences prefs;
	private SharedPreferences.Editor prefsEditor;
	private PersistentCookieStore myCookieStore;

	/* Default Method which will run first */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_listing); /*
											 * Retrieving the contents of the
											 * layout new_list_create_1
											 */
		prefs = getSharedPreferences(USER_PREFS, MODE_PRIVATE);
		prefsEditor = prefs.edit();
		myCookieStore = new PersistentCookieStore(this);
		confirmLogon();
		(new CurrentLocation(this, this)).execute();

	}

	public void clickCreateProd(View v) {
Log.v("HERE _____ I ____ AM","YEP");
		/* Category Spinner is declared */
		Spinner categoryOption = (Spinner) findViewById(R.id.categoryspinner);

		/* Edit Textboxes are declared */
		EditText prodTitle = (EditText) findViewById(R.id.prodtitle);
		EditText prodDesc = (EditText) findViewById(R.id.proddesc);
		EditText expPrice = (EditText) findViewById(R.id.expPrice);
		expPrice.setRawInputType(Configuration.KEYBOARD_12KEY);

		/* Calling a method based on the input */
		this.CreateProduct(categoryOption.getSelectedItem().toString(),
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

	}

	// LifeCycle end

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
		switch (item.getItemId()) {
		case R.id.action_Prod_cam:
			startActivity(new Intent(NewProd.this, AddImageToProd.class));
			break;
		case R.id.action_prod_setting:
			startActivity(new Intent(NewProd.this, UpdateProfile.class));
			break;
		case R.id.action_go_home:
			startActivity(new Intent(NewProd.this, SmartlisterHome.class));
			finish();
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}

	private void confirmLogon() {
		JSONObject jsonParams = new JSONObject();
		StringEntity entity;
		try {
			entity = new StringEntity(jsonParams.toString());
			AsyncHttpClient client = new AsyncHttpClient();
			client.setCookieStore(myCookieStore);
			client.put(getApplicationContext(),
					"http://www.marcstreeter.com/sl/authorized", entity,
					"application/json", new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(String response) {
							prefsEditor.putBoolean(AUTHORIZED, true);
							prefsEditor.commit();
						}

						@Override
						public void onFailure(int statusCode,
								org.apache.http.Header[] headers,
								byte[] responseBody, Throwable error) {
							prefsEditor.putBoolean(AUTHORIZED, false);
							prefsEditor.commit();
						}
					});
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	private void CreateProduct(String category, String title,
			String description, String exprice) {
		if (!prefs.getBoolean(AUTHORIZED, false)) {
			startActivity(new Intent(NewProd.this, Logon.class));
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
			jsonLocParams.put(P_LATITUDE, this.prefs.getFloat(P_LATITUDE,(float) 0.0));
			jsonLocParams.put(P_LONGITUDE, this.prefs.getFloat(P_LONGITUDE,(float) 0.0));
			jsonParams.put(LOCATION, jsonLocParams);
			jsonParams.put("listing_type", "for_sale");
			jsonParams.put(CATEG, category.toLowerCase());
			jsonParams.put(TITLE, title);
			jsonParams.put(DESCR, description);
			jsonParams.put(FEE, fee);
			jsonParams.put(P_RADIUS, this.prefs.getInt(P_RADIUS, 50));
			jsonParams.put(P_LANG, this.prefs.getString(P_LANG, "en").toLowerCase());
			jsonParams.put(P_CURRENCY, this.prefs.getString(P_CURRENCY, "USD").toLowerCase());
			jsonParams.put(OBO_IND, false);// defaulting for now

			StringEntity entity = new StringEntity(jsonParams.toString());
			Log.v("JSON SENDING",jsonParams.toString());
			AsyncHttpClient client = new AsyncHttpClient();
			client.setCookieStore(myCookieStore);
			client.put(getApplicationContext(),
					"http://www.marcstreeter.com/sl/list", entity,
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
