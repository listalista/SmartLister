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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;

public class CreateProposal extends FragmentActivity {
	
	public static final String USER_PREFS = "UserPrefs";

	
	public static final String SALE_ID = "listing_for_sale_id";
	public static final String OFFERFEE = "offer_fee";
	public static final String DESCR = "description";
	public static final String EXPIRY = "expires_in_days";
	
	public static final String AUTHORIZED = "authenticated";
	private SharedPreferences prefs;
	private SharedPreferences.Editor prefsEditor;
	private PersistentCookieStore myCookieStore;
	
	/* Default Method which will run first */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.proposal_request);
		

	}
	public void clickmakeOffer(View v) {
		

		/* Edit Textboxes are declared */
		EditText propPrice = (EditText) findViewById(R.id.propprice);
		EditText propDesc = (EditText) findViewById(R.id.propdesc);
		

		/* Calling a method based on the input */
		this.CreateOffer(propPrice.getText().toString(),
				propDesc.getText().toString());

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
		
		private void CreateOffer(String offerprice, String offerdesc) {
			if (!prefs.getBoolean(AUTHORIZED, false)) {
				startActivity(new Intent(CreateProposal.this, MainLogon.class));
				return;
			}
			
			/* Retrieving the values from SelectedProduct.java and storing it in variables */
	        Intent intent = getIntent();
	        String saleid = intent.getStringExtra("listing_for_sale_id");
	        int sid = Integer.parseInt(saleid);
	        
	        int expiry = 30;
	        
			try {
				JSONObject jsonParams = new JSONObject();
				
				jsonParams.put(SALE_ID, sid);
				jsonParams.put(OFFERFEE, offerprice);
				jsonParams.put(DESCR, offerdesc);
				jsonParams.put(EXPIRY, expiry);


				StringEntity entity = new StringEntity(jsonParams.toString());
				AsyncHttpClient client = new AsyncHttpClient();
				client.setCookieStore(myCookieStore);
				client.put(getApplicationContext(),
						"http://www.marcstreeter.com/sl/offer", entity,
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
									
									Toast.makeText(CreateProposal.this, "Offer Creation Failed. Try Again Later.!", Toast.LENGTH_SHORT).show();

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
