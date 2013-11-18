package com.example.smartlist;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;

import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.smartlist.MainLogon;
import com.example.smartlist.MainLogon.ErrorDialogFragment;
import com.example.smartlist.LocationUtils;
import com.example.smartlist.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationListener;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;

import android.location.Location;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

//--imported

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

import android.content.IntentSender;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
//--imported done

public class MainLogon extends FragmentActivity implements
LocationListener,
GooglePlayServicesClient.ConnectionCallbacks,
GooglePlayServicesClient.OnConnectionFailedListener {
	public static final String USER_PREFS = "UserPrefs";
	private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
	//--imported
	 // A request to connect to Location Services
    private LocationRequest mLocationRequest;

    // Stores the current instantiation of the location client in this object
    private LocationClient mLocationClient;

    // Handles to UI widgets
    private TextView mLatLng;
    private TextView mAddress;
    private ProgressBar mActivityIndicator;
    private TextView mConnectionState;
    private TextView mConnectionStatus;

    // Handle to SharedPreferences for this app
    SharedPreferences mPrefs;

    // Handle to a SharedPreferences editor
    SharedPreferences.Editor mEditor;

    /*
     * Note if updates have been turned on. Starts out as "false"; is set to "true" in the
     * method handleRequestSuccess of LocationUpdateReceiver.
     *
     */
    boolean mUpdatesRequested = false;

    /*
     * Initialize the Activity
     */
	//--imported done
	
	/*Default Method which will run first*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.smart_login);
        
        //-----imported
        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        // Get handles to the UI view objects
        //mLatLng = (TextView) findViewById(R.id.lat_lng);
        //mAddress = (TextView) findViewById(R.id.address);
        //mActivityIndicator = (ProgressBar) findViewById(R.id.address_progress);
        //mConnectionState = (TextView) findViewById(R.id.text_connection_state);
        //mConnectionStatus = (TextView) findViewById(R.id.text_connection_status);

        // Create a new global location parameters object
        mLocationRequest = LocationRequest.create();

        /*
         * Set the update interval
         */
        mLocationRequest.setInterval(LocationUtils.UPDATE_INTERVAL_IN_MILLISECONDS);

        // Use high accuracy
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        // Set the interval ceiling to one minute
        mLocationRequest.setFastestInterval(LocationUtils.FAST_INTERVAL_CEILING_IN_MILLISECONDS);

        // Note that location updates are off until the user turns them on
        mUpdatesRequested = false;

        // Open Shared Preferences
        mPrefs = getSharedPreferences(LocationUtils.SHARED_PREFERENCES, Context.MODE_PRIVATE);

        // Get an editor
        mEditor = mPrefs.edit();

        /*
         * Create a new location client, using the enclosing class to
         * handle callbacks.
         */
        mLocationClient = new LocationClient(this, this, this);
        //-----imported done
    }
    public void clickLogon(View v) {
		EditText usernameEmail = (EditText) findViewById(R.id.logonemail);
		EditText passwordEmail = (EditText) findViewById(R.id.logonpswd);
    	this.LogOn(usernameEmail.getText().toString(), passwordEmail.getText().toString());
    }
    public void clickRegister(View v) {
    	startActivity(new Intent(MainLogon.this, RegisterUser.class));
    }
    public void clickHelp(View v) {
		startActivity(new Intent(MainLogon.this, LogonHelp.class)); 
    }
    //Activity Lifecycle Overrides
    /*
     * Called when the Activity is no longer visible at all.
     * Stop updates and disconnect.
     */
    @Override
    public void onStop() {

        // If the client is connected
        if (mLocationClient.isConnected()) {
            stopPeriodicUpdates();
        }

        // After disconnect() is called, the client is considered "dead".
        mLocationClient.disconnect();

        super.onStop();
    }
    /*
     * Called when the Activity is going into the background.
     * Parts of the UI may be visible, but the Activity is inactive.
     */
    @Override
    public void onPause() {

        // Save the current setting for updates
        mEditor.putBoolean(LocationUtils.KEY_UPDATES_REQUESTED, mUpdatesRequested);
        mEditor.commit();

        super.onPause();
    }

    /*
     * Called when the Activity is restarted, even before it becomes visible.
     */
    @Override
    public void onStart() {
        super.onStart();

        /*
         * Connect the client. Don't re-start any requests here;
         * instead, wait for onResume()
         */
        mLocationClient.connect();
        

    }
    /*
     * Called when the system detects that this Activity is now visible.
     */
    @Override
    public void onResume() {
        super.onResume();

        // If the app already has a setting for getting location updates, get it
        if (mPrefs.contains(LocationUtils.KEY_UPDATES_REQUESTED)) {
            mUpdatesRequested = mPrefs.getBoolean(LocationUtils.KEY_UPDATES_REQUESTED, false);

        // Otherwise, turn off location updates until requested
        } else {
            mEditor.putBoolean(LocationUtils.KEY_UPDATES_REQUESTED, false);
            mEditor.commit();
        }

    }
    //LifeCycle end
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_logon, menu);
        return true;
    }
    private void LogOn(String username, String password){
    	try{
    		JSONObject jsonParams = new JSONObject();
    		jsonParams.put("username", username);
    		jsonParams.put("password", password);
    		StringEntity entity = new StringEntity(jsonParams.toString());
    		AsyncHttpClient client = new AsyncHttpClient();
    		PersistentCookieStore myCookieStore = new PersistentCookieStore(this);
    		client.setCookieStore(myCookieStore);
   			client.put(getApplicationContext(), "http://www.marcstreeter.com/sl/logon", entity, "application/json",new AsyncHttpResponseHandler() {
       			@Override
       			public void onSuccess(String response) {
       				try {
       					//parse JSON string into JSON object
       					JSONObject responseObj = new JSONObject(response);
       					//A] set preferences by iterating over json keys
       					//A] step 1: gain access to particular prefs
       					SharedPreferences settings = getSharedPreferences(USER_PREFS,MODE_PRIVATE);
       					SharedPreferences.Editor prefEditor = settings.edit();
       					//A] step 2: create iterator of JSON keys
       					Iterator<?> jsonKeys = responseObj.keys();
       					//A] Step 3: iterate over 
       						//A] Step 3.1: clear previous prefs
       					prefEditor.clear();
       					while(jsonKeys.hasNext()){
       						//A] Step 4: set preference
       						String key = (String)jsonKeys.next();
       						if( responseObj.get(key) instanceof String ){
       							prefEditor.putString(key, responseObj.getString(key));
       						}else if(responseObj.get(key) instanceof Boolean){
       							prefEditor.putBoolean(key, responseObj.getBoolean(key));
       						}else if(responseObj.get(key) instanceof Float){
       							prefEditor.putInt(key, responseObj.getInt(key));
       						}else{//prefEditor.putString(key, responseObj.get(key).toString());
       							Log.v("LOGON","JSON response not added to pref: " + responseObj.get(key).toString());
       							continue;
       						}
       						Log.v("PREF-SENSE",responseObj.getString(key));
       					}
       					//prefEditor.putString("alias", responseObj.getString("alias"));
       					//prefEditor.putString("lang", jsonResponse.getString("lang"));
       					//A] Step 4: commit 
       					prefEditor.commit();
       				} catch (JSONException e) {
       					// TODO Auto-generated catch block
       					Log.v("ERROR",e.toString());
       					Log.v("ERROR-MESSAGE",e.getMessage());
       				}
       				Log.v("RECEIVED", response);
       				Intent logOnIntent = new Intent(MainLogon.this, SmartlisterHome.class);
       	    		startActivity(logOnIntent); /*New Intent is called*/
       			}
       			@Override
       			public void onFailure(int statusCode, org.apache.http.Header[] headers, byte[] responseBody, Throwable error)
       			{
       				try {
       					String response = new String(responseBody, "UTF-8");
						Log.v("RESPONSE","code["+Integer.valueOf(statusCode).toString()+"] headers:" + headers.toString() +" body:" + response);
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						Log.v("ERROR",e.toString());
						Log.v("ERROR-MESSAGE",e.getMessage());
					}

       	    		EditText passwordEmail = (EditText) findViewById(R.id.logonpswd);
       	    		passwordEmail.setText("");
       			}
    		});
    	}catch(JSONException e){
			Log.v("CAUGHT",e.toString());
			Log.v("CAUGHT",e.getMessage());
			
        }catch(UnsupportedEncodingException e){
			Log.v("CAUGHT",e.toString());
			Log.v("CAUGHT",e.getMessage());
        }
    }
    
    //--------------------- Google Services Logon Overrides
	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {

        /*
         * Google Play services can resolve some errors it detects.
         * If the error has a resolution, try sending an Intent to
         * start a Google Play services activity that can resolve
         * error.
         */
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(
                        this,
                        LocationUtils.CONNECTION_FAILURE_RESOLUTION_REQUEST);
                /*
                * Thrown if Google Play services canceled the original
                * PendingIntent
                */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
        	// If no resolution is available, display a dialog to the user with the error.
            showErrorDialog(connectionResult.getErrorCode());
        }
		
	}
	@Override
	public void onConnected(Bundle bundle) {
		 //mConnectionStatus.setText(R.string.connected);
		Log.v("GPSACTIVTY",getString(R.string.connected));
		//if (mUpdatesRequested) {
	      startPeriodicUpdates();
	    //}
		//getAddress();
	}
	@Override
	public void onDisconnected() {
		//mConnectionStatus.setText(R.string.disconnected);
		Log.v("GPSACTIVITY",getString(R.string.disconnected));
	}
	@Override
	public void onLocationChanged(Location location) {

        // Report to the UI that the location was updated
       //mConnectionStatus.setText(R.string.location_updated);
        Log.v("GPSACTIVITY",getString(R.string.location_updated));
        // In the UI, set the latitude and longitude to the value received
        //mLatLng.setText(LocationUtils.getLatLng(this, location));
        Log.v("GPSACTIVITYLATLON",LocationUtils.getLatLng(this, location));
    }  
	// --- Google services logon overrides END
	/**
     * Verify that Google Play services is available before making a request.
     *
     * @return true if Google Play services is available, otherwise false
     */
    private boolean servicesConnected() {

        // Check that Google Play services is available
        int resultCode =
                GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        // If Google Play services is available
        if (ConnectionResult.SUCCESS == resultCode) {
            // In debug mode, log the status
            Log.d(LocationUtils.APPTAG, getString(R.string.play_services_available));

            // Continue
            return true;
        // Google Play services was not available for some reason
        } else {
            // Display an error dialog
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode, this, 0);
            if (dialog != null) {
                ErrorDialogFragment errorFragment = new ErrorDialogFragment();
                errorFragment.setDialog(dialog);
                errorFragment.show(getSupportFragmentManager(), LocationUtils.APPTAG);
            }
            return false;
        }
    }
	/**
     * Invoked by the "Get Address" button.
     * Get the address of the current location, using reverse geocoding. This only works if
     * a geocoding service is available.
     *
     * @param v The view object associated with this method, in this case a Button.
     */
    // For Eclipse with ADT, suppress warnings about Geocoder.isPresent()
    //@SuppressLint("NewApi")
    public void getAddress(View v) {
//
//        // In Gingerbread and later, use Geocoder.isPresent() to see if a geocoder is available.
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD && !Geocoder.isPresent()) {
//            // No geocoder is present. Issue an error message
//            Toast.makeText(this, R.string.no_geocoder_available, Toast.LENGTH_LONG).show();
//            return;
//        }

        if (servicesConnected()) {

            // Get the current location
        	Log.v("WHAT","IS HAPPENING");
            Location currentLocation = mLocationClient.getLastLocation();
            Log.v("WHAT","IS HAPPENING2");
            // Turn the indefinite activity indicator on
            //mActivityIndicator.setVisibility(View.VISIBLE);
            Log.v("WHAT","IS HAPPENING3");
            // Start the background task
            (new GetAddressTask(this)).execute(currentLocation);
        }
    }
	

    /**
     * Show a dialog returned by Google Play services for the
     * connection error code
     *
     * @param errorCode An error code returned from onConnectionFailed
     */
    private void showErrorDialog(int errorCode) {

        // Get the error dialog from Google Play services
        Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(
            errorCode,
            this,
            LocationUtils.CONNECTION_FAILURE_RESOLUTION_REQUEST);

        // If Google Play services can provide an error dialog
        if (errorDialog != null) {

            // Create a new DialogFragment in which to show the error dialog
            ErrorDialogFragment errorFragment = new ErrorDialogFragment();

            // Set the dialog in the DialogFragment
            errorFragment.setDialog(errorDialog);

            // Show the error dialog in the DialogFragment
            errorFragment.show(getSupportFragmentManager(), LocationUtils.APPTAG);
        }
    }

    /**
     * In response to a request to start updates, send a request
     * to Location Services
     */
    private void startPeriodicUpdates() {

        mLocationClient.requestLocationUpdates(mLocationRequest, this);
        //mConnectionState.setText(R.string.location_requested);
        Log.v("GPSACTIVITY",getString(R.string.location_requested));
    }
	/**
     * In response to a request to stop updates, send a request to
     * Location Services
     */
    private void stopPeriodicUpdates() {
        mLocationClient.removeLocationUpdates(this);
        //mConnectionState.setText(R.string.location_updates_stopped);
        Log.v("GPSACTIVITY",getString(R.string.location_updates_stopped));
    }
	/**
     * Define a DialogFragment to display the error dialog generated in
     * showErrorDialog.
     */
    public static class ErrorDialogFragment extends DialogFragment {

        // Global field to contain the error dialog
        private Dialog mDialog;

        /**
         * Default constructor. Sets the dialog field to null
         */
        public ErrorDialogFragment() {
            super();
            mDialog = null;
        }

        /**
         * Set the dialog to display
         *
         * @param dialog An error dialog
         */
        public void setDialog(Dialog dialog) {
            mDialog = dialog;
        }

        /*
         * This method must return a Dialog to the DialogFragment.
         */
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return mDialog;
        }
    }
}
