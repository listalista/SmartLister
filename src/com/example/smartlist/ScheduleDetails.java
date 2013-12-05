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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ScheduleDetails extends FragmentActivity implements OnClickListener {
	
	public static final String USER_PREFS = "UserPrefs";
	public static final String AUTHORIZED = "authenticated";
	private SharedPreferences prefs;
	private SharedPreferences.Editor prefsEditor;
	private PersistentCookieStore myCookieStore;
	public boolean buyerind = false;
	public int transid = -1;
	
	/*Default Method which will run first*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_details);  /*Retrieving the contents of the layout buyer_product_select*/
        
        onLoadDetails();
        
        
        /*Start Map button in Product Select page is declared for onClickListener*/
        Button trl = (Button) findViewById(R.id.tracklocation);
        trl.setOnClickListener(this);
        
        /*Cancel button in Product Select page is declared for onClickListener*/
        Button cdp = (Button) findViewById(R.id.cancelsch);
        cdp.setOnClickListener(this);
        
        /*Cancel button in Product Select page is declared for onClickListener*/
        Button dc = (Button) findViewById(R.id.dealconfirm);
        dc.setOnClickListener(this);
        
        
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
  		
  		onLoadDetails();
  	}

  	// LifeCycle end
  	
  	
  	public void onLoadDetails() {
  		
  		myCookieStore = new PersistentCookieStore(this);
		prefs = getSharedPreferences(USER_PREFS, MODE_PRIVATE);
		prefsEditor = prefs.edit();
	
        
        /* Edit Textboxes are declared */
		TextView prodTitle = (TextView) findViewById(R.id.schprodtitle);
		TextView schtime = (TextView) findViewById(R.id.scheduletime);
		TextView schloc = (TextView) findViewById(R.id.schlocation);
		TextView schdescp = (TextView) findViewById(R.id.schdesc);
		Button dc = (Button) findViewById(R.id.dealconfirm);
		
		/* Retrieving the values from Lisings.java and storing it in variables */
        Intent intent = getIntent();
		
		String title = intent.getStringExtra("title");
		String description = intent.getStringExtra("description");
		String time = intent.getStringExtra("time");
		String location = intent.getStringExtra("location");
		transid = intent.getIntExtra("trans_id", -1);
		buyerind = intent.getBooleanExtra("buyer_ind", false);
		
		
		prodTitle.setText(title);
		schtime.setText(time);
		schloc.setText(location);
		schdescp.setText(description);
		
		if(buyerind == true) {
        	dc.setEnabled(false);
        }
  	}


    /*Executing the onClick listener method when a button is clicked*/
    @Override
    public void onClick(View v) {	
		if(v.getId() == R.id.tracklocation) {  /*Calling the seller_menu when Submit is clicked*/
			startActivity(new Intent(ScheduleDetails.this, TrackLocation.class)); /*New Intent is called*/
		}
		else if(v.getId() == R.id.cancelsch) {  /*Calling the seller_menu when Submit is clicked*/
			startActivity(new Intent(ScheduleDetails.this, ScheduleList.class)); /*New Intent is called*/
		}
		else if(v.getId() == R.id.dealconfirm) {  /*Calling the seller_menu when Submit is clicked*/
			dealClosure();
		}
    }
    
    
    public void dealClosure() {
    	/* Radio Groups are declared */
		RadioGroup group=(RadioGroup) findViewById(R.id.dealclose);
		RadioButton radioyes =(RadioButton) findViewById(R.id.closeyes);
		RadioButton radiono =(RadioButton) findViewById(R.id.closeno);
		
		boolean closed_ind = false;
		
		if(buyerind == true) {
			Toast.makeText(ScheduleDetails.this, "You are a Buyer, You cannot close a Transaction", Toast.LENGTH_SHORT).show();
		} else {
			if( group.getCheckedRadioButtonId() == R.id.closeyes) {
				closed_ind = true;
			} else if( group.getCheckedRadioButtonId() == R.id.closeno) {
				closed_ind = false;
			}
			
			try {
				JSONObject jsonParams = new JSONObject();
				
				jsonParams.put("trans_id", transid);
				jsonParams.put("closed_ind", closed_ind);
				


				StringEntity entity = new StringEntity(jsonParams.toString());
				AsyncHttpClient client = new AsyncHttpClient();
				client.setCookieStore(myCookieStore);
				client.put(getApplicationContext(),
						"http://www.marcstreeter.com/sl/closetransaction", entity,
						"application/json", new AsyncHttpResponseHandler() {
							@Override
							public void onSuccess(String response) {
								startActivity(new Intent(ScheduleDetails.this, SmartlisterHome.class));
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
									
									Toast.makeText(ScheduleDetails.this, "Offer Response Failed. Try Again Later.!", Toast.LENGTH_SHORT).show();

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
            startActivity(new Intent(ScheduleDetails.this, SmartlisterHome.class));
            finish();
            break;
        default:
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

}