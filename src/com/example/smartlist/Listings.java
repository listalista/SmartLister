package com.example.smartlist;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.smartlist.Listings;
import com.example.smartlist.R;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import android.widget.AbsListView.OnScrollListener;
import android.widget.Toast;

public class Listings extends FragmentActivity implements OnScrollListener{
	private int listingsPerPage = 50;
	private int currentPage=1;
	private SharedPreferences prefs;
	private ArrayList<Listing> listings;
	public ListingAdapter listingAdapter;
	GridView gridview;
	private static final String USER_PREFS = "UserPrefs";
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.listings);
	    prefs = this.getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE);
	    listings = new ArrayList<Listing>();
	    gridview = (GridView) findViewById(R.id.gridview);
	    listingAdapter = new ListingAdapter(this,listings);
	    gridview.setAdapter(listingAdapter);
	    gridview.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id){
	        	ListingForSale listing = (ListingForSale)listings.get(position);
	        	if(listing != null){
	        		//look at this later http://stackoverflow.com/questions/2139134/how-to-send-an-object-from-one-android-activity-to-another-using-intents
	        		//Listing listing = (Listing) parent.getAdapter().getItem(position);
	        		//Toast.makeText(Listings.this, listing.getTitle().toLowerCase(), Toast.LENGTH_SHORT).show();
	        		Intent listingDetail = new Intent(Listings.this, SelectedProduct.class);
	        		listingDetail.putExtra("lat", listing.getLocLat());
	        		listingDetail.putExtra("lon", listing.getLocLon());
	        		listingDetail.putExtra("title", listing.getTitle());
	        		listingDetail.putExtra("description", listing.getDescription());
	        		listingDetail.putExtra("fee", listing.getFee());
	        		listingDetail.putExtra("currency", listing.getCurrency());
	        		listingDetail.putExtra("category", listing.getCategory());
	        		listingDetail.putExtra("obo", listing.getObo());
	        		listingDetail.putExtra("created", listing.getCreationTimeStamp());
	        		
	        		startActivity(listingDetail);
	        	}
	        }
	    });
	    (new CurrentLocation(this,this, new ResponseHandler(){
	    	@Override
	    	public void callBack(){
	    		getListings((double)prefs.getFloat("lat",0),(double)prefs.getFloat("lon",0),prefs.getInt("dist", 50));
	    	}
	    })).execute();
	    
	    
	}
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		Toast.makeText(Listings.this, "smartlister", Toast.LENGTH_SHORT).show();
		Log.v("smartlister","GOT HERE!");
	}
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		Toast.makeText(Listings.this, "GOT onScroll HERE", Toast.LENGTH_SHORT).show();
		Log.v("smartlister","GOT HERE!");
		
	}
	private void getListings(double lat, double lon, int dist){
    	try{
    		JSONObject jsonParams = new JSONObject();
    		jsonParams.put("lat",lat);
    		jsonParams.put("lon",lon);
    		jsonParams.put("dist",dist);
    		jsonParams.put("listings_per_page",getListingsPerPage());
    		jsonParams.put("current_page",getCurrentPage());
    		
    		StringEntity entity = new StringEntity(jsonParams.toString());
    		AsyncHttpClient client = new AsyncHttpClient();
    		PersistentCookieStore myCookieStore = new PersistentCookieStore(this);
    		client.setCookieStore(myCookieStore);
   			client.put(getApplicationContext(),"http://www.marcstreeter.com/sl/listings", entity, "application/json",new AsyncHttpResponseHandler() {
       			@Override
       			public void onSuccess(String response) {
       				try {
       					JSONObject responseObj = new JSONObject(response);
       					JSONArray listingsJson = responseObj.getJSONArray("listing");
       					for (int i = 0; i < listingsJson.length(); ++i) {
       					    JSONObject curListing = listingsJson.getJSONObject(i);
       					    if(!curListing.getString("listing_type").equals("listingforsale")){ continue;
       					    }//skip listings that are not for sale....for now
   					    	String newCategory = curListing.getString("category");
   					    	String newCreated = curListing.getString("created");
   					    	String newCurrency = curListing.getString("currency");
   					    	String newDescription = curListing.getString("descr");
   					    	int newFee = curListing.getInt("fee");
   					    	double newLocLat = curListing.getDouble("loc_lat");
   					    	double newLocLon = curListing.getDouble("loc_lon");
   					    	boolean newObo = curListing.getBoolean("obo");
   					    	int newRadius = curListing.getInt("radius");
   					    	String newTitle = curListing.getString("title");
   					    	listingAdapter.add(new ListingForSale(newCategory,newCreated,newCurrency,newDescription,newFee,newLocLat,newLocLon,newObo,newRadius,newTitle));   
       					}

       					listingAdapter.addAll(listings);
       					listingAdapter.notifyDataSetChanged();
       				} catch (JSONException e) {
       					Log.v("JSONException","Listings");
       					Log.v("ERROR",e.toString());
       					Log.v("ERROR-MESSAGE",e.getMessage());
       				}
       			}
       			@Override
       			public void onFailure(int statusCode, org.apache.http.Header[] headers, byte[] responseBody, Throwable error)
       			{
       				try {
       					String response = new String(responseBody, "UTF-8");
						Log.v("RESPONSE","code["+Integer.valueOf(statusCode).toString()+"] headers:" + headers.toString() +" body:" + response);
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						Log.v("smartlister ERROR",e.toString());
						Log.v("ERROR-MESSAGE",e.getMessage());
					}
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_logon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
        case R.id.action_new_prod:
        	startActivity(new Intent(Listings.this, NewProd.class));
            break;
        default:
            return super.onOptionsItemSelected(item);
        }
        return true;
    }
	private void setCurrentPage(int currentPage){ this.currentPage = currentPage; }
	private int getCurrentPage(){ return this.currentPage; }
	private int getListingsPerPage(){ return this.listingsPerPage; }
}
