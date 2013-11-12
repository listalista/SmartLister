package com.example.smartlist;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;

import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainLogon extends Activity implements OnClickListener {
	public static final String USER_PREFS = "UserPrefs";
	
	/*Default Method which will run first*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.smart_login); /*Retrieving the contents of the layout smart_login*/
        
        /*Log On is declared for onClickListener*/
        Button lo = (Button) findViewById(R.id.logon);
        lo.setOnClickListener(this);
        
        /*Forgot Password is declared for onClickListener*/
        Button fp = (Button) findViewById(R.id.forgotpassword);
        fp.setOnClickListener(this);
        
        /*Register is declared for onClickListener*/
        Button ru = (Button) findViewById(R.id.registeruser);
        ru.setOnClickListener(this);
    }
    
    /*Executing the onClick listener method when a button is clicked*/
    @Override
    public void onClick(View v) {	
    	if(v.getId() == R.id.logon) {  /*Calling the home_page when LogOn is clicked*/
    		/* This needs to be put into a try block -- so if u/pw are not  
    		   entered we don't leave to new intent */
    		EditText usernameEmail = (EditText) findViewById(R.id.logonemail);
    		EditText passwordEmail = (EditText) findViewById(R.id.logonpswd);
    		this.LogOn(usernameEmail.getText().toString(), passwordEmail.getText().toString());
    		//logOnIntent.putExtra("usernameEmail", usernameEmail.getText().toString());
    		//logOnIntent.putExtra("passwordEmail", passwordEmail.getText().toString());
    		
    	} else if(v.getId() == R.id.forgotpassword) {  /*Calling the forget_password when Forgot Password is clicked*/
    		startActivity(new Intent(MainLogon.this, ForgotPassword.class)); /*New Intent is called*/
    	} else if(v.getId() == R.id.registeruser) {  /*Calling the new_user_register when Register is clicked*/
    		startActivity(new Intent(MainLogon.this, RegisterUser.class)); /*New Intent is called*/
    	}
    }


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
       				String[] prefs = {"alias","lang"};
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
}
