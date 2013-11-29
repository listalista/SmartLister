package com.example.smartlist;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;

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


public class UpdateProfile extends FragmentActivity {
	
	public static final String USER_PREFS = "UserPrefs";
	public static final String PASSWORD_VERIFY = "password";
	public static final String DISPLAY_NAME = "alias";
	public static final String BROADCAST_DISTANCE = "radius";
	public static final String PREFERRED_CURRENCY = "currency";
	public static final String PREFERRED_LANG = "language";
	public static final String AUTHORIZED = "authenticated";
	private SharedPreferences settings;
	private SharedPreferences.Editor prefsEditor;
	private PersistentCookieStore myCookieStore;
	
	/*Default Method which will run first*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_user_profile);  /*Retrieving the contents of the layout update_user_profile*/
        
        /*EditBoxes are declared */
    	EditText dispName = (EditText) findViewById(R.id.updatedispname);
    	EditText brodDist = (EditText) findViewById(R.id.broddist);
    	
		/* Spinners are declared */
		Spinner currencyOption = (Spinner) findViewById(R.id.preferredcurrency);
		Spinner langOption = (Spinner) findViewById(R.id.preferredlanguage);
        
        
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
		myCookieStore = new PersistentCookieStore(this);
		settings = getSharedPreferences(USER_PREFS, MODE_PRIVATE);
		prefsEditor = settings.edit();
		
		//EditBoxes are declared 
    	EditText dispName = (EditText) findViewById(R.id.updatedispname);
    	EditText brodDist = (EditText) findViewById(R.id.broddist);
    	
		// Spinners are declared 
		Spinner currencyOption = (Spinner) findViewById(R.id.preferredcurrency);
		Spinner langOption = (Spinner) findViewById(R.id.preferredlanguage);
		
		dispName.setText(settings.getString(DISPLAY_NAME, "")); // Sending the value of Display Name to the Layout Textbox
		
		// Getting the value of BroadcastDistance and sending it to Layout Textbox
		String defBrodDist = String.valueOf(settings.getInt(BROADCAST_DISTANCE, 50));
		brodDist.setText(defBrodDist);
		
		// Getting the value of Preferred Language and sending it to Layout Spinner 
		String defLang = settings.getString(PREFERRED_LANG, "en");
		if(defLang == "" || defLang == "en") {
			defLang = "ENGLISH";
		}
		else if(defLang == "es") {
			defLang = "ESPANOL";
		}
		else {
			defLang = "ENGLISH";
		}
		ArrayAdapter myAdap1 = (ArrayAdapter) langOption.getAdapter();
		int spinnerPosition1 = myAdap1.getPosition(defLang);
		langOption.setSelection(spinnerPosition1);
		
		// Getting the value of Preferred Currency and sending it to Layout Spinner 
		String defCurr = settings.getString(PREFERRED_CURRENCY, "USD");
		defCurr = defCurr.toUpperCase();
		if(defCurr == "") {
			defCurr = "USD";
		}
		else if(defCurr == "USD" || defCurr == "CAD" || defCurr == "EUR" || defCurr == "GBP") {
			defCurr = PREFERRED_CURRENCY;
		}
		else {
			defCurr = "USD";
		}
		ArrayAdapter myAdap2 = (ArrayAdapter) currencyOption.getAdapter();
		int spinnerPosition2 = myAdap2.getPosition(defCurr);
		currencyOption.setSelection(spinnerPosition2);
	}

	// LifeCycle end
	
    public void clickUpdatePref(View v) {
    	
    	/*EditBoxes are declared */
    	EditText dispName = (EditText) findViewById(R.id.updatedispname);
    	EditText brodDist = (EditText) findViewById(R.id.broddist);
    	EditText pwdValid = (EditText) findViewById(R.id.passwordvalid);
    	
		/* Spinners are declared */
		Spinner currencyOption = (Spinner) findViewById(R.id.preferredcurrency);
		Spinner langOption = (Spinner) findViewById(R.id.preferredlanguage);
    	

		/* Calling a method based on the input */
		this.UpdatePreferrence(dispName.getText().toString(),
				brodDist.getText().toString(), currencyOption.getSelectedItem().toString(),
				langOption.getSelectedItem().toString(), pwdValid.getText().toString());

	}
	
	/* Logon Validation to check whether he is a valid user */
	

	private void UpdatePreferrence(String aliasName, String broadDist,
			String preferCurr, String preferLang, String pwdVerify) {
		if (!settings.getBoolean(AUTHORIZED, false)) {
			startActivity(new Intent(UpdateProfile.this, MainLogon.class));
			return;
		}
		int distance;
		try{
			distance = Integer.parseInt(broadDist);
		}catch(Error e){
			distance = 50;
		}
		if(distance == 0) {
			distance = 50;
		}
		
		String convertLang;
		if(preferLang == "ESPANOL") {
			convertLang = "es";
		} else {
			convertLang = "en";
		}
			
		try {
			JSONObject jsonParams = new JSONObject();
			jsonParams.put(PASSWORD_VERIFY, pwdVerify);
			jsonParams.put(DISPLAY_NAME, aliasName);
			jsonParams.put(BROADCAST_DISTANCE, distance);
			jsonParams.put(PREFERRED_CURRENCY, preferCurr);
			jsonParams.put(PREFERRED_LANG, convertLang);
			

			StringEntity entity = new StringEntity(jsonParams.toString());
			AsyncHttpClient client = new AsyncHttpClient();
			client.setCookieStore(myCookieStore);
			client.put(getApplicationContext(),
					"http://www.marcstreeter.com/sl/setprefs", entity,
					"application/json", new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(String response) {
							try {
		       					//parse JSON string into JSON object
		       					JSONObject responseObj = new JSONObject(response);
		       					//A] set preferences by iterating over json keys
		       					//A] step 1: gain access to particular prefs
		       					SharedPreferences settings = getSharedPreferences(USER_PREFS,MODE_PRIVATE);
		       					prefsEditor = settings.edit();
		       					//prefEditor.putBoolean(AUTHORIZED, true);
		       					//A] step 2: create iterator of JSON keys
		       					Iterator<?> jsonKeys = responseObj.keys();
		       					//A] Step 3: iterate over 
		       						//A] Step 3.1: clear previous prefs
		       					//prefEditor.clear();
		       					while(jsonKeys.hasNext()){
		       						//A] Step 4: set preference
		       						String key = (String)jsonKeys.next();
		       						if( responseObj.get(key) instanceof String ){
		       							prefsEditor.putString(key, responseObj.getString(key));
		       						}else if(responseObj.get(key) instanceof Boolean){
		       							prefsEditor.putBoolean(key, responseObj.getBoolean(key));
		       						}else if(responseObj.get(key) instanceof Float){
		       							prefsEditor.putFloat(key, (float)responseObj.getDouble(key));
		       						}else if(responseObj.get(key) instanceof Integer){
		       							prefsEditor.putInt(key, responseObj.getInt(key));
		       						}else{
		       							Log.v("UPDATE PREFERRENCE","JSON response not added to pref: " + responseObj.get(key).toString());
		       							continue;
		       						}
		       						Log.v("PREF-SENSE",responseObj.getString(key));
		       					}
		       					//A] Step 4: commit 
		       					prefsEditor.commit();
		       				} catch (JSONException e) {
		       					// TODO Auto-generated catch block
		       					Log.v("ERROR",e.toString());
		       					Log.v("ERROR-MESSAGE",e.getMessage());
		       				}
		       				Log.v("RECEIVED", response);
							//startActivity(new Intent(UpdateProfile.this, MainLogon.class));
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
								
								Toast.makeText(UpdateProfile.this, "Preference Update Failed. Try Again.!", Toast.LENGTH_SHORT).show();
								
								//startActivity(new Intent(UpdateProfile.this, MainLogon.class));
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
