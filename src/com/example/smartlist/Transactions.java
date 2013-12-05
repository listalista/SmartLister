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

public class Transactions extends FragmentActivity implements OnScrollListener{
	private int listingsPerPage = 50;
	private int currentPage=1;
	private SharedPreferences prefs;
	private ArrayList<Transaction> transactions;
	public TransactionAdapter transactionAdapter;
	GridView gridview;
	private static final String USER_PREFS = "UserPrefs";
	public static final String AUTHORIZED = "authenticated";

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.transactions);
	    prefs = this.getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE);
	    transactions = new ArrayList<Transaction>();
	    gridview = (GridView) findViewById(R.id.gridviewtrans);
	    transactionAdapter = new TransactionAdapter(this,transactions);
	    gridview.setAdapter(transactionAdapter);
	    gridview.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id){
	        	//ListingForSale listing = (ListingForSale)listings.get(position);
	        	Transaction transaction = transactionAdapter.getItem(position);
	        	if(transactions != null){
	        		//look at this later http://stackoverflow.com/questions/2139134/how-to-send-an-object-from-one-android-activity-to-another-using-intents
	        		//Listing listing = (Listing) parent.getAdapter().getItem(position);
	        		//Toast.makeText(Listings.this, listing.getTitle().toLowerCase(), Toast.LENGTH_SHORT).show();
	        		Intent offerDetail = new Intent(Transactions.this, ScheduleDetails.class);
	        		offerDetail.putExtra("trans_id", transaction.getId());
	        		offerDetail.putExtra("location", transaction.getSchedule().getDescription());
	        		offerDetail.putExtra("description", transaction.getDescription());
	        		offerDetail.putExtra("time", transaction.getSchedule().getDateTime());
	        		offerDetail.putExtra("buyer_ind", transaction.getOffer().isBuyer());

	        		
	        		offerDetail.putExtra("title", transaction.getListing().getTitle());
	        		offerDetail.putExtra("sale_prod_id", transaction.getListing().getId());
	        		
	        		
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
		Toast.makeText(Transactions.this, "smartlister", Toast.LENGTH_SHORT).show();
		Log.v("smartlister","GOT HERE!");
	}
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		Toast.makeText(Transactions.this, "GOT onScroll HERE", Toast.LENGTH_SHORT).show();
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
   			client.put(getApplicationContext(),"http://www.marcstreeter.com/sl/transactions", entity, "application/json",new AsyncHttpResponseHandler() {
       			@Override
       			public void onSuccess(String response) {
       				try {
       					JSONObject responseObj = new JSONObject(response);
       					if(!responseObj.has("transaction")){
       						Toast.makeText(Transactions.this, "No Transactions were made so far.", Toast.LENGTH_SHORT).show();
       						return;
       					}
       					JSONArray transactionsJson = responseObj.getJSONArray("transaction");
       					transactionAdapter.clear();
       					for (int i = 0; i < transactionsJson.length(); ++i) {
       					    JSONObject curListing = transactionsJson.getJSONObject(i);
       					
       					    JSONObject listingJSON = curListing.getJSONObject("listing");
    					    JSONObject offerJSON = curListing.getJSONObject("offer");
    						JSONObject scheduleJSON = curListing.getJSONObject("schedule");
       					    Boolean acceptedInd = offerJSON.getBoolean("accepted_ind");
       					    Boolean cancelledInd = offerJSON.getBoolean("cancelled_ind");
       					    Boolean buyerInd = offerJSON.getBoolean("buyer_ind");
       					    String comment = offerJSON.getString("comment");
       					    //JSONArray counteroffer = curListing.getJSONArray("previous_offer");
       					    Integer expDays = offerJSON.getInt("expires_in_days");
       					    Integer countid = offerJSON.getInt("counter_offer_id");
       					    if (countid == null) {
       					    	countid = 0;
       					    }
       					    Integer fee = offerJSON.getInt("fee");
       					    String message = offerJSON.getString("message");
    					    Integer offer_id = offerJSON.getInt("offer_id");
    					    Boolean viewed = offerJSON.getBoolean("viewed");
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
       					    
       					    String timeShed = String.valueOf(scheduleJSON.getString("time"));
       					    String description =scheduleJSON.getString("description");
       					    						
       					    Listing listing = new ListingForSale( v_id,  v_category,  v_creationTimeStamp,  v_currency, v_description, v_fee, v_locLat, v_locLon,v_obo, v_radius, v_title);
       					    Schedule schedule = new Schedule(timeShed, description);
       					    Offer offer = new Offer(acceptedInd, cancelledInd, buyerInd, comment, expDays, countid, fee, listing, message, offer_id, viewed);
   			
       					    int trans_id =curListing.getInt("trans_id");
       					    boolean cancelled =curListing.getBoolean("cancelled");
       					    int expiry =curListing.getInt("expires_in_days");
       					    String t_description = curListing.getString("description");
   					    	
   					    	
   					    	transactionAdapter.add(new Transaction(trans_id, cancelled, expiry, t_description, offer, listing, schedule));   
   	       					transactionAdapter.notifyDataSetChanged();
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
        		startActivity(new Intent(Transactions.this, MainLogon.class));
        	}else{
        		startActivity(new Intent(Transactions.this, SmartlisterHome.class));
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