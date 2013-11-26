package com.example.smartlist;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class TrackLocation extends FragmentActivity implements LocationSource, LocationListener {
	
	
	final int RQS_GooglePlayServices = 1;
	TextView tvLocInfo;
	
	LocationManager myLocationManager = null;
	OnLocationChangedListener myLocationListener = null;
	Criteria myCriteria;
	 
    // Google Map
    private GoogleMap googleMap;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tracking_map);
        
        tvLocInfo = (TextView)findViewById(R.id.locinfo);
 
        try {
            // Loading map
            initilizeMap();
 
        } catch (Exception e) {
            e.printStackTrace();
        }
 
    }
 
    /**
     * function to load map. If map is not created it will create it for you
     * */
    private void initilizeMap() {
        if (googleMap == null) {
            googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            
            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
            else {
            	
            	googleMap.setMyLocationEnabled(true);
            	googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            	myCriteria = new Criteria();
                myCriteria.setAccuracy(Criteria.ACCURACY_FINE);
                myLocationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
                
                
            	
            }
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_logon, menu);
        return true;
    }
    
 
    @Override
    protected void onResume() {
        super.onResume();
        
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
        
        if (resultCode == ConnectionResult.SUCCESS){
         Toast.makeText(getApplicationContext(), 
           "Connected to Smartlister Map", 
           Toast.LENGTH_LONG).show();
         
         //Register for location updates using a Criteria, and a callback on the specified looper thread.
         myLocationManager.requestLocationUpdates(
           0L,    //minTime
           0.0f,    //minDistance
           myCriteria,  //criteria
           this,    //listener
           null);   //looper
         
         //Replaces the location source of the my-location layer.
         googleMap.setLocationSource(this);
         
        }else{
         GooglePlayServicesUtil.getErrorDialog(resultCode, this, RQS_GooglePlayServices); 
        }
    }
    
    @Override
    protected void onPause() {
     googleMap.setLocationSource(null);
     myLocationManager.removeUpdates(this);
        
     super.onPause();
    }

    @Override
    public void activate(OnLocationChangedListener listener) {
     myLocationListener = listener;
    }

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		  if (myLocationListener != null) {
			   myLocationListener.onLocationChanged(location);
			   
			   double lat = location.getLatitude();
			   double lon = location.getLongitude();
			   
			   tvLocInfo.setText(
			     "lattitude: " + lat + "\n" +
			     "longitude: " + lon);
			   
			   LatLng latlng= new LatLng(location.getLatitude(), location.getLongitude());
			   googleMap.animateCamera(CameraUpdateFactory.newLatLng(latlng));
		  }
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deactivate() {
		// TODO Auto-generated method stub
		myLocationListener = null;
	}
 
}
