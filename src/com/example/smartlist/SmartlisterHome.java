package com.example.smartlist;


import java.io.UnsupportedEncodingException;

import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
 
public class SmartlisterHome extends Activity implements OnItemClickListener {
	public static final String USER_PREFS = "UserPrefs";
	public static final String AUTHORIZED = "authenticated";
	private SharedPreferences prefs;
	private SharedPreferences.Editor prefsEditor;
	private PersistentCookieStore myCookieStore;
    
	ListView listView; //Declaring Listview in public
 
	//Default Method which will run first
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.smartlister_main_menu); 
        listView = (ListView) findViewById(R.id.slmenu);
        String[] smartlistermenu = getResources().getStringArray(R.array.Smartlister_Menu);
        //confirmLogon();
        
        //Pushing the array content into list view and capture the onClick action
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, smartlistermenu));
        listView.setOnItemClickListener(this);
    }
 
    //onClickListener action upon clicking an option from the list
    @Override
    public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
    	
    	Object GetLabel = listView.getItemAtPosition(position);
    	if (GetLabel.toString().equals("Update Profile Information")) {
    	     Intent intent = new Intent(SmartlisterHome.this, UpdateProfile.class);
    	     startActivity(intent);                  
    	} 
    	else if (GetLabel.toString().equals("Change Password")) {
   	     	Intent intent = new Intent(SmartlisterHome.this, ChangePassword.class);
   	     	startActivity(intent);                  
    	}
    	else if (GetLabel.toString().equals("Seller Menu")) {
   	     	Intent intent = new Intent(SmartlisterHome.this, SellerMenu.class);
   	     	startActivity(intent);             
    	}
    	else if (GetLabel.toString().equals("Make Offers")) {
   	     	Intent intent = new Intent(SmartlisterHome.this, Offers.class);
   	     	startActivity(intent);             
    	}
    	else if (GetLabel.toString().equals("Scheduled Transactions")) {
   	     	Intent intent = new Intent(SmartlisterHome.this, Transactions.class);
   	     	startActivity(intent);             
    	}
    	else if (GetLabel.toString().equals("Sign Out")) {
    		
   	     	logOff();            
    	}
    }
    @Override
    public void onResume(){
    	super.onResume();
    	prefs = getSharedPreferences(USER_PREFS,MODE_PRIVATE);
    	prefsEditor = prefs.edit();
    	if(!prefs.getBoolean(AUTHORIZED, false)){
    		startActivity(new Intent(SmartlisterHome.this,MainLogon.class));
        }else{
        	TextView userAlias = (TextView) findViewById(R.id.textView2);
            userAlias.setText(prefs.getString("alias", "nothing"));
        }
    }
    private void logOff(){
    		StringEntity entity;
			try {
				entity = new StringEntity("{}");
				AsyncHttpClient client = new AsyncHttpClient();
	    		PersistentCookieStore myCookieStore = new PersistentCookieStore(this);
	    		client.setCookieStore(myCookieStore);
	   			client.put(getApplicationContext(),"http://www.marcstreeter.com/sl/logout", entity, "application/json",new AsyncHttpResponseHandler() {
	       			@Override
	       			public void onSuccess(String response) {
	       				Intent intent = new Intent(SmartlisterHome.this, Listings.class);
	       	   	     	startActivity(intent); 
	       	   	     	prefsEditor.clear();
	       	   	     	prefsEditor.commit();
	       			}
	       			@Override
	       			public void onFailure(int statusCode, org.apache.http.Header[] headers, byte[] responseBody, Throwable error)
	       			{
	       				Log.v("ERROR","ON LOGGING OUT");
	       				Intent intent = new Intent(SmartlisterHome.this, Listings.class);
	       	   	     	startActivity(intent); 
	       	   	     	prefsEditor.clear();
	       	   	     	prefsEditor.commit();
	       			}
	    		});
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
    		
    	
    }
}
