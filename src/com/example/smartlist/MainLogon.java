package com.example.smartlist;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;

import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.smartlist.MainLogon;
import com.example.smartlist.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;

import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


//--imported done

public class MainLogon extends FragmentActivity {
	public static final String USER_PREFS = "UserPrefs";
	public static final String AUTHORIZED = "authenticated";

	/*Default Method which will run first*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.smart_login);
        (new CurrentLocation(this,this)).execute();
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
    
    
    /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_logon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
        case R.id.action_new_prod:
        	startActivity(new Intent(MainLogon.this, NewProd.class));
            break;
        default:
            return super.onOptionsItemSelected(item);
        }
        return true;
    } */
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
       					prefEditor.putBoolean(AUTHORIZED, true);
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
       				//Intent logOnIntent = new Intent(MainLogon.this, SmartlisterHome.class);
       	    		//startActivity(logOnIntent); /*New Intent is called*/
       				finish();
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
}
