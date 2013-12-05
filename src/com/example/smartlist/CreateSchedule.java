package com.example.smartlist;

import java.io.UnsupportedEncodingException;

import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class CreateSchedule extends FragmentActivity  {
	
public static final String USER_PREFS = "UserPrefs";
	
	public static final String AUTHORIZED = "authenticated";
	private SharedPreferences prefs;
	private SharedPreferences.Editor prefsEditor;
	private PersistentCookieStore myCookieStore;
	
	/*Default Method which will run first*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_schedule);  /*Retrieving the contents of the layout seller_menu*/
        
    }


    public void clickmakeOffer(View v) {
		
    	
    	String dateFormat = "";

		/* Edit Textboxes are declared */
		DatePicker date=(DatePicker) findViewById(R.id.scheduledate);
		TimePicker time=(TimePicker) findViewById(R.id.scheduletime);
		EditText location=(EditText) findViewById(R.id.schedulelocation);
		EditText comment=(EditText) findViewById(R.id.sellproduct);
		
		/* Retrieving the values from Lisings.java and storing it in variables */
        Intent intent = getIntent();
		
        int offerid = intent.getIntExtra("offer_id", -1);
		String cancelInd = String.valueOf(intent.getBooleanExtra("cancelled_ind", false));
		String acceptInd = String.valueOf(intent.getBooleanExtra("accepted_ind", false));
		String buyerInd = String.valueOf(intent.getBooleanExtra("buyer_ind", false));
		String n_status = intent.getStringExtra("status");
		String n_comment = intent.getStringExtra("comment");
		boolean n_indicator = intent.getBooleanExtra("indicator", false);
		
		int year = date.getYear();
		int month = date.getMonth() + 1;
		int day = date.getDayOfMonth();
		int hour = time.getCurrentHour();
    	int min = time.getCurrentMinute();
    	String sec = "00";
    	
    	String hr, mn;
    	
    	if(hour>-1 && hour<10) {
    		hr = "0" + hour;
    	} else {
    		hr = String.valueOf(hour);
    	}
    	
    	if(min>-1 && min<10) {
    		mn = "0" + min;
    	} else {
    		mn = String.valueOf(min);
    	}
    	
    	dateFormat = (year + "-" + month + "-" + day + " " + hr + ":" + mn + ":" + sec);
    	
    	String n_loc = location.getText().toString();
    	String n_desc = comment.getText().toString();
    	
    	this.CreateSchedule(offerid, cancelInd, acceptInd, n_comment, dateFormat, n_loc, n_desc, n_indicator);
		
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
 		
 		private void CreateSchedule(int offerid, String cancelInd, String acceptInd, String n_comment, String dateFormat, String n_loc, String n_desc, boolean n_indicator) {
			if (!prefs.getBoolean(AUTHORIZED, false)) {
				startActivity(new Intent(CreateSchedule.this, MainLogon.class));
				return;
			}
					
	        
			try {
				JSONObject jsonParams = new JSONObject();
				if (n_indicator == true) {
					
					JSONObject jsonTransParams = new JSONObject();
					JSONObject jsonSchParams = new JSONObject();
					
					jsonParams.put("offer_id", offerid);
					jsonParams.put("transaction_details", jsonTransParams);
					jsonTransParams.put("expires_in_days", 30);
					jsonTransParams.put("description", n_desc);
					jsonTransParams.put("schedule", jsonSchParams);
					jsonSchParams.put("description", n_loc);
					jsonSchParams.put("time", dateFormat);
				} else {
					
					JSONObject jsonTransParams = new JSONObject();
					JSONObject jsonSchParams = new JSONObject();
					
					jsonParams.put("offer_id", offerid);
					jsonParams.put("cancelled_ind", cancelInd);
					jsonParams.put("accepted_ind", acceptInd);
					jsonParams.put("comment", n_comment);
					jsonParams.put("transaction_details", jsonTransParams);
					jsonTransParams.put("expires_in_days", 30);
					jsonTransParams.put("description", n_desc);
					jsonTransParams.put("schedule", jsonSchParams);
					jsonSchParams.put("description", n_loc);
					jsonSchParams.put("time", dateFormat);
				}
				


				StringEntity entity = new StringEntity(jsonParams.toString());
				AsyncHttpClient client = new AsyncHttpClient();
				client.setCookieStore(myCookieStore);
				client.put(getApplicationContext(),
						"http://www.marcstreeter.com/sl/transaction", entity,
						"application/json", new AsyncHttpResponseHandler() {
							@Override
							public void onSuccess(String response) {
								startActivity(new Intent(CreateSchedule.this, SmartlisterHome.class));
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
									
									Toast.makeText(CreateSchedule.this, "Schedule Creation Failed. Try Again Later.!", Toast.LENGTH_SHORT).show();

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
