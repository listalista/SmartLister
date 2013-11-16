package com.example.smartlist;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;

public class RegisterUser extends Activity implements OnClickListener {
	public static final String USER_PREFS = "UserPrefs";
	public static final String INVALID_INPUT = "ERROR_INVALID_INPUT";
	
	/*Default Method which will run first*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logon_register);  /*Retrieving the contents of the layout new_user_register*/
        
        /*Create button in New User Registration is declared for onClickListener*/
        Button nur = (Button) findViewById(R.id.createuser);
        nur.setOnClickListener(this);
    }

    
    /*Executing the onClick listener method when a button is clicked*/
    @Override
    public void onClick(View v) {
    	if(v.getId() == R.id.createuser) {  /*Calling the home_page when Create is clicked*/
    		EditText usernameET = (EditText) findViewById(R.id.newtypemail);
    		EditText passwordConfirmationET = (EditText) findViewById(R.id.newretypepswd);
    		EditText passwordET = (EditText) findViewById(R.id.newpassword);
    		EditText aliasET = (EditText) findViewById(R.id.dispname);
    		String username = usernameET.getText().toString();
    		String passwordConfirmation = passwordConfirmationET.getText().toString();
    		String password = passwordET.getText().toString();
    		String alias = aliasET.getText().toString();
    		String lang = "en";//currently only english is supported, we'll add other languages later
    		if(validInput(username,password,passwordConfirmation,alias,lang)){
    			this.register(username,password,alias,lang);
    		}else{
    			sendAlert(getStringResourceByName(INVALID_INPUT));
    		}
    		
    	}
    }
    private boolean validInput(String username, String password, String passwordConfirmation,String alias, String lang){
    	if(password.equals(passwordConfirmation)){
        	return true;
    	}
    	return false;
    }
    private String getStringResourceByName(String aString) {
        String packageName = getPackageName();
        int resId = getResources().getIdentifier(aString, "string", packageName);
        return getString(resId);
    }
    private void sendAlert(String message){
    	Context context = getApplicationContext();
    	int duration = Toast.LENGTH_LONG;

    	Toast toast = Toast.makeText(context, message, duration);
    	//toast.setGravity(Gravity.TOP|Gravity.LEFT, 0, 0);
    	toast.setGravity(Gravity.TOP, 0, 0);
    	toast.show();
    }
    private void register(String username, String password, String alias, String lang){
    	try{
    		JSONObject jsonParams = new JSONObject();
    		jsonParams.put("email", username);
    		//since we are focusing usernames on emails currently, this will need to be changed later to accommodate for Twitter,Google+, Facebook, etc
    		jsonParams.put("password", password);
    		jsonParams.put("alias", alias);
    		jsonParams.put("lang", lang);
    		StringEntity jsonString = new StringEntity(jsonParams.toString());
    		AsyncHttpClient client = new AsyncHttpClient();
    		PersistentCookieStore myCookieStore = new PersistentCookieStore(this);
    		client.setCookieStore(myCookieStore);
   			client.put(getApplicationContext(), "http://www.marcstreeter.com/sl/register", jsonString, "application/json",new AsyncHttpResponseHandler() {
       			@Override
       			public void onSuccess(String response) {
       				try {
       					//parse JSON string into JSON object
       					JSONObject responseObj = new JSONObject(response);
       					if(responseObj.getBoolean("registered")){
       						//show success message
       						sendAlert(getStringResourceByName(responseObj.getString("status")));
               	    		//clear preferences
           					SharedPreferences settings = getSharedPreferences(USER_PREFS,MODE_PRIVATE);
               	    		SharedPreferences.Editor prefEditor = settings.edit();
           					prefEditor.clear();
           					//go to Logon Screen
       						startActivity(new Intent(RegisterUser.this, MainLogon.class)); /*New Intent is called*/
       	       	    		finish();//hopefully prevents an activity from coming back here
       					}else{
       						//show failure message and reason
       					}
       				} catch (JSONException e) {
       					// TODO Auto-generated catch block
       					Log.v("PUTS","I'M here you putz");
       					Log.v("ERROR",e.toString());
       					Log.v("ERROR-MESSAGE",e.getMessage());
       				}
       				Log.v("RECEIVED", response);
       			}
       			@Override
       			public void onFailure(int statusCode, org.apache.http.Header[] headers, byte[] responseBody, Throwable error)
       			{
       				try{
       					String response = new String(responseBody, "UTF-8");
						Log.v("RESPONSE",response);
       					JSONObject responseObj = new JSONObject(response);
       					EditText emailAddress = (EditText) findViewById(R.id.newtypemail);
           	    		EditText password = (EditText) findViewById(R.id.newpassword);
           	    		EditText passwordRetyped = (EditText) findViewById(R.id.newretypepswd);
           	    		ArrayList<EditText> fields = new ArrayList<EditText>();
           	    		fields.add(emailAddress);
           	    		fields.add(password);
           	    		fields.add(passwordRetyped);
           	    		Iterator<EditText>fieldIterator = fields.iterator();
           	    		while(fieldIterator.hasNext()){
           	    			EditText field = fieldIterator.next();
           	    			field.setText("");
           	    		}
           	    		sendAlert(getStringResourceByName(responseObj.getString("status")));
       					Log.v("RESPONSEFAILURE","code["+Integer.valueOf(statusCode).toString()+"] headers:" + headers.toString() +" body :" + response);
					}catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
       					Log.v("EXCEPTION","UnsupportedEncodingException");
						Log.v("ERROR",e.toString());
						Log.v("ERROR-MESSAGE",e.getMessage());
					}catch (JSONException e) {
       					// TODO Auto-generated catch block
       					Log.v("EXCEPTION","JSONException");
       					Log.v("ERROR",e.toString());
       					Log.v("ERROR-MESSAGE",e.getMessage());
       				}catch(Exception e){
       		        	Log.v("EXCEPTION","onFailure");
       					Log.v("CAUGHT",e.toString());
       					Log.v("CAUGHT",e.getMessage());	
       				}
       			}
    		});
    	}catch(JSONException e){
    		Log.v("EXCEPTION","JSONException");
			Log.v("CAUGHT",e.toString());
			Log.v("CAUGHT",e.getMessage());
			
        }catch(UnsupportedEncodingException e){
    		Log.v("EXCEPTION","UnsupportedEncodingException");
			Log.v("CAUGHT",e.toString());
			Log.v("CAUGHT",e.getMessage());
        }catch(Exception e){
        	Log.v("Exception","Exception Register");
			Log.v("CAUGHT",e.toString());
			Log.v("CAUGHT",e.getMessage());
        }
    } 
}
