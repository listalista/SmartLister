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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import android.widget.AbsListView.OnScrollListener;
import android.widget.Toast;

public class Offers extends FragmentActivity implements OnScrollListener{
	private int listingsPerPage = 50;
	private int currentPage=1;
	private SharedPreferences prefs;
	private ArrayList<Offer> offers;
	public OffersAdapter offerAdapter;
	GridView gridview;
	private static final String USER_PREFS = "UserPrefs";
	public static final String AUTHORIZED = "authenticated";

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.offers);
	    prefs = this.getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE);
	    offers = new ArrayList<Offer>();
	    gridview = (GridView) findViewById(R.id.gridviewoffer);
	    offerAdapter = new OffersAdapter(this,offers);
	    gridview.setAdapter(offerAdapter);
	    gridview.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id){
	        	//ListingForSale listing = (ListingForSale)listings.get(position);
	        	Offer offer = offerAdapter.getItem(position);
	        	if(offers != null){
	        		//look at this later http://stackoverflow.com/questions/2139134/how-to-send-an-object-from-one-android-activity-to-another-using-intents
	        		//Listing listing = (Listing) parent.getAdapter().getItem(position);
	        		//Toast.makeText(Listings.this, listing.getTitle().toLowerCase(), Toast.LENGTH_SHORT).show();
	        		Intent offerDetail = new Intent(Offers.this, RespondProposal.class);
	        		offerDetail.putExtra("offer_id", offer.getId());
	        		offerDetail.putExtra("counter_offer_id", offer.getCounterofferid());
	        		offerDetail.putExtra("cancelled_ind", offer.isCancelled());
	        		offerDetail.putExtra("accepted_ind", offer.isAccepted());
	        		offerDetail.putExtra("buyer_ind", offer.isBuyer());
	        		offerDetail.putExtra("comment", offer.getComment());
	        		offerDetail.putExtra("message", offer.getMessage());
	        		offerDetail.putExtra("fee", offer.getFee());
	        		offerDetail.putExtra("expires_in_days", offer.getExpiresInDays());
	        		
	        		offerDetail.putExtra("title", offer.getListing().getTitle());
	        		offerDetail.putExtra("sale_prod_id", offer.getListing().getId());
	        		
	        		
	        		startActivity(offerDetail);
	        	}
	        }
	    });  
	}
	@Override
	public void onResume(){
	    super.onResume();
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
		Toast.makeText(Offers.this, "smartlister", Toast.LENGTH_SHORT).show();
		Log.v("smartlister","GOT HERE!");
	}
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		Toast.makeText(Offers.this, "GOT onScroll HERE", Toast.LENGTH_SHORT).show();
		Log.v("smartlister","GOT HERE!");
		
	}
	private void getListings(double lat, double lon, int dist){
    	try{
    		
    		JSONObject jsonParams = new JSONObject();
    		//JSONObject jsonParamsCriteria = new JSONObject();
    		
    		jsonParams.put("listings_per_page",getListingsPerPage());
    		jsonParams.put("current_page",getCurrentPage());
   
    		
    		StringEntity entity = new StringEntity(jsonParams.toString());
    		AsyncHttpClient client = new AsyncHttpClient();
    		PersistentCookieStore myCookieStore = new PersistentCookieStore(this);
    		client.setCookieStore(myCookieStore);
   			client.put(getApplicationContext(),"http://www.marcstreeter.com/sl/offers", entity, "application/json",new AsyncHttpResponseHandler() {
       			@Override
       			public void onSuccess(String response) {
       				try {
       					JSONObject responseObj = new JSONObject(response);
       					if(!responseObj.has("offer")){
       						Toast.makeText(Offers.this, "No Offers were made so far.", Toast.LENGTH_SHORT).show();
       						return;
       					}
       					JSONArray offersJson = responseObj.getJSONArray("offer");
       					offerAdapter.clear();
       					for (int i = 0; i < offersJson.length(); ++i) {
       					    JSONObject curListing = offersJson.getJSONObject(i);
       					
       					    
       					    Boolean acceptedInd = curListing.getBoolean("accepted_ind");
       					    Boolean cancelledInd = curListing.getBoolean("cancelled_ind");
       					    Boolean buyerInd = curListing.getBoolean("buyer_ind");
       					    String comment = curListing.getString("comment");
       					    //JSONArray counteroffer = curListing.getJSONArray("previous_offer");
       					    Integer expDays = curListing.getInt("expires_in_days");
       					    Integer countid = curListing.getInt("counter_offer_id");
       					    if (countid == null) {
       					    	countid = 0;
       					    }
       					    Integer fee = curListing.getInt("fee");
       					    JSONObject listingJSON = curListing.getJSONObject("listing");
       					    int v_id = listingJSON.getInt("listing_for_sale_id");
       					    String v_currency = listingJSON.getString("currency");
       					    String v_category = listingJSON.getString("category");
       					    String v_creationTimeStamp = listingJSON.getString("creationTimeStamp");
       					    String v_description = listingJSON.getString("descr");
       					    Integer v_fee = listingJSON.getInt("fee");
       					    Double v_locLat = listingJSON.getDouble("loc_lat");
       					    Double v_locLon = listingJSON.getDouble("loc_lon");
       					    boolean v_obo = listingJSON.getBoolean("obo");
       					    int v_radius = listingJSON.getInt("radius");
       					    String v_title =listingJSON.getString("title");
       					    						
       					    Listing listing = new ListingForSale( v_id,  v_category,  v_creationTimeStamp,  v_currency, v_description, v_fee, v_locLat, v_locLon,v_obo, v_radius, v_title);
       					    String message = curListing.getString("message");
       					    Integer offerid = curListing.getInt("offer_id");
       					    Boolean viewed = curListing.getBoolean("viewed");
   					    	
   					    	
   					    	offerAdapter.add(new Offer(acceptedInd,cancelledInd,buyerInd,comment,expDays, countid, fee,listing,message,offerid,viewed));   
   	       					offerAdapter.notifyDataSetChanged();
       					}

       					//listingAdapter.addAll(listings);
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
        getMenuInflater().inflate(R.menu.go_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
        case R.id.action_user_home:
        	if(!prefs.getBoolean(AUTHORIZED,false)){
        		startActivity(new Intent(Offers.this, MainLogon.class));
        	}else{
        		startActivity(new Intent(Offers.this, SmartlisterHome.class));
        	}
            break;
        default:
            return super.onOptionsItemSelected(item);
        }
        return true;
    }
	//private void setCurrentPage(int currentPage){ this.currentPage = currentPage; }
	private int getCurrentPage(){ return this.currentPage; }
	private int getListingsPerPage(){ return this.listingsPerPage; }
}