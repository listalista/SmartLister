package com.example.smartlist;


import android.app.Activity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

import android.location.Location;

import com.example.smartlist.LocationUtils;
import android.content.Context;
import android.content.IntentSender;
import android.content.SharedPreferences;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class CurrentLocation extends AsyncTask<TextView, Void, Location> implements
LocationListener,
GooglePlayServicesClient.ConnectionCallbacks,
GooglePlayServicesClient.OnConnectionFailedListener {

	public static final String USER_PREFS = "UserPrefs";
	private Location location;
	// Store the context passed to the AsyncTask when the system instantiates it.
    private Context localContext;
    private Activity localActivity;
    // A request to connect to Location Services
    private LocationRequest mLocationRequest;
    // Stores the current instantiation of the location client in this object
    private LocationClient mLocationClient;
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
    private ResponseHandler listener;
    
    /*
     * Initialize the Activity
     */
    // Constructor called by the system to instantiate the task
    public CurrentLocation(Context context, Activity activity, ResponseHandler listener){
    	super();
    	this.listener = listener;
    	// Required by the semantics of AsyncTask
        // Set a Context for the background task
        localContext = context;
        localActivity = activity;
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
        mPrefs = localContext.getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE);

        // Get an editor
        mEditor = mPrefs.edit();
        /*
         * Create a new location client, using the enclosing class to
         * handle callbacks.
         */
       //mLocationClient = new LocationClient(this, this, this);
        mLocationClient = new LocationClient(localContext, this, this);
        mLocationClient.connect();
        
        //-----imported done
    
    }
    public CurrentLocation(Context context, Activity activity) {
        this(context, activity, new ResponseHandler(){
	    	@Override
	    	public void callBack(){}
	    });
    }
	@Override
	protected Location doInBackground(TextView... params) {
		while(location==null){ /* wait - eventually should add check to make sure that is recent */}
		return this.location;
	}
	@Override
	protected void onPostExecute(Location result){
		listener.callBack();
		mLocationClient.disconnect();
	}
	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(
                        localActivity,
                        LocationUtils.CONNECTION_FAILURE_RESOLUTION_REQUEST);
                /*
                * Thrown if Google Play services canceled the original
                * PendingIntent
                */
            } catch (IntentSender.SendIntentException e) {
            	Log.v("PRINTING STACK TRACE","FROM CURRENT LOCATION");
                e.printStackTrace();
            }
        } else {
        	// If no resolution is available, display a dialog to the user with the error.
            //showErrorDialog(connectionResult.getErrorCode());
        	Log.v("onConnectionFailed",localContext.getString(connectionResult.getErrorCode()));
        }
		
	}
	@Override
	public void onConnected(Bundle bundle) {
		Log.v("GOT onconnected","GOT HERE!");
		Location loc = getLocation();
		if(loc == null){return;}
		setLocation(loc);
	}
	@Override
	public void onDisconnected() {
		//mConnectionStatus.setText(R.string.disconnected);
		Log.v("GPSACTIVITY",localContext.getString(R.string.disconnected));
	}
	@Override
	public void onLocationChanged(Location location) {
        // Report to the UI that the location was updated
        //Log.v("GPSACTIVITY",localContext.getString(R.string.location_updated));
        // In the UI, set the latitude and longitude to the value received
        //Log.v("GPSACTIVITYLATLON",LocationUtils.getLatLng(localContext, location));
    }
	/**
     * Verify that Google Play services is available before making a request.
     *
     * @return true if Google Play services is available, otherwise false
     */
	private void setLocation(Location loc){
		//set location to preferences
		mEditor.putFloat("lat", (float)loc.getLatitude());
		mEditor.putFloat("lon", (float)loc.getLongitude());
		//http://stackoverflow.com/questions/8077530/android-get-current-timestamp
		mEditor.putString("latlonTS", Long.toString(System.nanoTime()));
		mEditor.commit();
		location = loc;
	}
    private boolean servicesConnected() {

        // Check that Google Play services is available
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(localContext);

        // If Google Play services is available
        if (ConnectionResult.SUCCESS == resultCode) {
            // In debug mode, log the status
            Log.d(LocationUtils.APPTAG, localContext.getString(R.string.play_services_available));

            // Continue
            return true;
        // Google Play services was not available for some reason
        } else {
         Log.v("GPS", "SERVICES NOT CONNECTED");
         //   // Display an error dialog
         //   Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode, localActivity, 0);
         //   if (dialog != null) {
         //       ErrorDialogFragment errorFragment = new ErrorDialogFragment();
         //       errorFragment.setDialog(dialog);
         //       errorFragment.show(getSupportFragmentManager(), LocationUtils.APPTAG);
         //   }
            return false;
        }
    }

    /**
     * Invoked by the "Get Location" button.
     *
     * Calls getLastLocation() to get the current location
     *
     * @param v The view object associated with this method, in this case a Button.
     */
    public Location getLocation() {
        // If Google Play Services is available
        if (servicesConnected()) {
            // Get the current location
            Location currentLocation = mLocationClient.getLastLocation();
            return currentLocation;
        }else{ return null;}
    }
    public void getAddress(Location currentLocation) {
        (new GetAddressTask(localContext)).execute(currentLocation);
    }
}
