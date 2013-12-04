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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;

public class RespondProposal extends FragmentActivity {
	
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
		
		fieldValidation();
		

	}
	public void clickmakeOffer(View v) {
		

		/* Edit Textboxes are declared */
		RadioGroup group=(RadioGroup) findViewById(R.id.propstatus);
		RadioButton radioacc=(RadioButton) findViewById(R.id.propaccept);
		RadioButton radiorej=(RadioButton) findViewById(R.id.propdecline);
		RadioButton radiocount=(RadioButton) findViewById(R.id.propcounter);
		EditText countoffer=(EditText) findViewById(R.id.counteroffer);
		EditText comment=(EditText) findViewById(R.id.propresultdesc);
		
		/* Retrieving the values from Lisings.java and storing it in variables */
        Intent intent = getIntent();
		
        int offerid = intent.getIntExtra("offer_id", -1);
        int countofferid = intent.getIntExtra("counter_offer_id", 0);
		String cancelInd = String.valueOf(intent.getBooleanExtra("cancelled_ind", false));
		String acceptInd = String.valueOf(intent.getBooleanExtra("accepted_ind", false));
		String buyerInd = String.valueOf(intent.getBooleanExtra("buyer_ind", false));
		String commentOffer = intent.getStringExtra("comment");
		String messageOffer = intent.getStringExtra("message");
		int fee = intent.getIntExtra("fee", 0); 
		int expiry = intent.getIntExtra("expires_in_days", 30);
		
		String status = "";
		if( group.getCheckedRadioButtonId() == R.id.propaccept) {
			status = "accept";
		} else if( group.getCheckedRadioButtonId() == R.id.propdecline) {
			status = "reject";
		} else if( group.getCheckedRadioButtonId() == R.id.propcounter) {
			status = "counter";
		}
		
		// CODE YET TO BE COMPLETED

		/* Calling a method based on the input */
		/*this.CreateOffer(propPrice.getText().toString(),
				propDesc.getText().toString());*/

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
			
			fieldValidation();
		}

		// LifeCycle end
		
		public void fieldValidation() {
			
			
			RadioGroup group=(RadioGroup) findViewById(R.id.propstatus);
			RadioButton radioacc=(RadioButton) findViewById(R.id.propaccept);
			RadioButton radiorej=(RadioButton) findViewById(R.id.propdecline);
			RadioButton radiocount=(RadioButton) findViewById(R.id.propcounter);
			EditText countoffer=(EditText) findViewById(R.id.counteroffer);
			EditText comment=(EditText) findViewById(R.id.propresultdesc);
			TextView ptitle = (TextView) findViewById(R.id.offprodtitle);
			TextView offee = (TextView) findViewById(R.id.offeramount);
			TextView offdesc = (TextView) findViewById(R.id.offdesc);
			TextView offstatus = (TextView) findViewById(R.id.offerstatus);
			Button confirm=(Button) findViewById(R.id.confirmprop);
			
			
			
			/* Retrieving the values from Lisings.java and storing it in variables */
	        Intent intent = getIntent();
			
	        int offerid = intent.getIntExtra("offer_id", -1);
	        int countofferid = intent.getIntExtra("counter_offer_id", 0);
			String cancelInd = String.valueOf(intent.getBooleanExtra("cancelled_ind", false));
			String acceptInd = String.valueOf(intent.getBooleanExtra("accepted_ind", false));
			String commentOffer = intent.getStringExtra("comment");
			String messageOffer = intent.getStringExtra("message");
			int fee = intent.getIntExtra("fee", 0); 
			int expiry = intent.getIntExtra("expires_in_days", 30);
			String prodTitle = intent.getStringExtra("title");
			
			if(cancelInd == "null") {
				cancelInd = "false";
			}
			if(acceptInd == "null") {
				acceptInd = "false";
			}
			
			String status = "";
			if(cancelInd == "true" && acceptInd == "true") {
				status = "Accepted";
				group.setEnabled(false);
				countoffer.setEnabled(false);
				comment.setEnabled(false);
				confirm.setEnabled(false);				
			} 
			else if(cancelInd == "true" && acceptInd == "false") {
				status = "Declined";
				group.setEnabled(false);
				countoffer.setEnabled(false);
				comment.setEnabled(false);
				confirm.setEnabled(false);				
			}
			else if(cancelInd == "false" && acceptInd == "false") {
				status = "NewOffer";
			}
			
			ptitle.setText(prodTitle);
			offee.setText(fee);
			offstatus.setText(status);
			if(status == "NewOffer") {
				offdesc.setText(messageOffer);
			} else {
				offdesc.setText(commentOffer);
			}
			
			
			group.setOnCheckedChangeListener(new OnCheckedChangeListener() 
	           {
				
				EditText countoffer=(EditText) findViewById(R.id.counteroffer);
				EditText comment=(EditText) findViewById(R.id.propresultdesc);

				@Override
		        public void onCheckedChanged(RadioGroup group, int checkedId) {
		            switch(checkedId) {
		            case R.id.propaccept:
		                // 'Accept' checked
		                break;
		            case R.id.propdecline:
		                // 'Decline' checked
		            	
		                break;
		            case R.id.propcounter:
		                // 'Counter Offer' checked
		            	countoffer.setEnabled(true);
						comment.setEnabled(true);
		                break;
		            }
		        }
	           });
			
			
				
		}
		
		private void CreateOffer(String offerprice, String offerdesc) {
			if (!prefs.getBoolean(AUTHORIZED, false)) {
				startActivity(new Intent(RespondProposal.this, MainLogon.class));
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
						"http://www.marcstreeter.com/sl/offerresponse", entity,
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
									
									Toast.makeText(RespondProposal.this, "Offer Creation Failed. Try Again Later.!", Toast.LENGTH_SHORT).show();

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
	        	boolean autStatus = prefs.getBoolean(AUTHORIZED,false);
	        	Log.v("Authentication", String.valueOf(autStatus));
	        	if(autStatus == false){
	        		startActivity(new Intent(RespondProposal.this, MainLogon.class));
	        	}else{
	        		startActivity(new Intent(RespondProposal.this, SmartlisterHome.class));
	        	}
	            break;
	        default:
	            return super.onOptionsItemSelected(item);
	        }
	        return true;
	    }

}