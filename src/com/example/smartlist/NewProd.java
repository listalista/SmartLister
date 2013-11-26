package com.example.smartlist;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;

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
import android.widget.Spinner;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class NewProd extends FragmentActivity {
	
	public static final String USER_PREFS = "UserPrefs";
	
	/*Default Method which will run first*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_listing);  /*Retrieving the contents of the layout new_list_create_1*/
        (new CurrentLocation(this,this)).execute();
            
    }
    
    
    public void clickCreateProd(View v) {
    	
    	/*Category Spinner is declared*/
        Spinner categoryOption = (Spinner) findViewById(R.id.categoryspinner);
        
        /* Edit Textboxes are declared */
        EditText prodTitle = (EditText) findViewById(R.id.prodtitle);
		EditText prodDesc = (EditText) findViewById(R.id.proddesc);
		EditText expPrice = (EditText) findViewById(R.id.expPrice);
		
		/* Calling a method based on the input */
    	this.CreateProduct(categoryOption.getSelectedItem().toString(), prodTitle.getText().toString(), prodDesc.getText().toString(), expPrice.getText().toString());
        
    }
    
  //Activity Lifecycle Overrides
    /*
     * Called when the Activity is no longer visible at all.
     * Stop updates and disconnect.
     */
    @Override
    public void onStop() {
        super.onStop();
    }
    /*
     * Called when the Activity is going into the background.
     * Parts of the UI may be visible, but the Activity is inactive.
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
         * Connect the client. Don't re-start any requests here;
         * instead, wait for onResume()
         */
     
    }
    /*
     * Called when the system detects that this Activity is now visible.
     */
    @Override
    public void onResume() {
        super.onResume();

    }
    //LifeCycle end

    
    /* Inflating the Menu options */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.product_options, menu);
        return true;
    }
    
    
    /* Performing action upon selecting a Menu Item */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
        case R.id.action_Prod_cam:
        	startActivity(new Intent(NewProd.this, AddImageToProd.class));
            break;
        case R.id.action_prod_setting:
            startActivity(new Intent(NewProd.this, UpdateProfile.class));
            break;
        case R.id.action_go_home:
            startActivity(new Intent(NewProd.this, SmartlisterHome.class));
            finish();
            break;
        default:
            return super.onOptionsItemSelected(item);
        }
        return true;
    }
    
    private void CreateProduct(String category, String title, String description, String expprice){
    	try{
    		JSONObject jsonParams = new JSONObject();
    		jsonParams.put("category", category);
    		jsonParams.put("title", title);
    		jsonParams.put("description", description);
    		jsonParams.put("fee", expprice);
    		StringEntity entity = new StringEntity(jsonParams.toString());
    		AsyncHttpClient client = new AsyncHttpClient();
   			client.put(getApplicationContext(), "http://www.marcstreeter.com/sl/list", entity, "application/json",new AsyncHttpResponseHandler() {
       			@Override
       			public void onSuccess(String response) {
       				try {
       					//parse JSON string into JSON object
       					JSONObject responseObj = new JSONObject(response);
       					//A] set preferences by iterating over json keys
       					//A] step 1: gain access to particular prefs
       					SharedPreferences settings = getSharedPreferences(USER_PREFS,MODE_PRIVATE);
       					SharedPreferences.Editor prefEditor = settings.edit();
       					prefEditor.commit();
       				} catch (JSONException e) {
       					// TODO Auto-generated catch block
       					Log.v("ERROR",e.toString());
       					Log.v("ERROR-MESSAGE",e.getMessage());
       				}
       				Log.v("RECEIVED", response);
       				startActivity(new Intent(NewProd.this, SellerMenu.class));
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

}
