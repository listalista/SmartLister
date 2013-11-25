package com.example.smartlist;


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
 
public class SmartlisterHome extends Activity implements OnItemClickListener {
	public static final String USER_PREFS = "UserPrefs";
    
	ListView listView; //Declaring Listview in public
 
	//Default Method which will run first
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.smartlister_main_menu);  //Retrieving the contents of the layout smartlister_main_menu
        listView = (ListView) findViewById(R.id.slmenu); //Declaring the List View
        //Retrieving the array content from strings.xml file
        String[] smartlistermenu = getResources().getStringArray(R.array.Smartlister_Menu);
        SharedPreferences settings = getSharedPreferences(USER_PREFS,MODE_PRIVATE);
        Log.v("AUTHENTICATED",String.valueOf(settings.getBoolean("authenticated", false)));
        if(settings.getBoolean("authenticated", false)){
            TextView userAlias = (TextView) findViewById(R.id.textView2);
            userAlias.setText(settings.getString("alias", "nothing"));
        }
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
    	else if (GetLabel.toString().equals("Buy a Product")) {
   	     	Intent intent = new Intent(SmartlisterHome.this, SearchProduct.class);
   	     	startActivity(intent);             
    	}
    	else if (GetLabel.toString().equals("Active Chat History")) {
   	     	Intent intent = new Intent(SmartlisterHome.this, ChatList.class);
   	     	startActivity(intent);             
    	}
    	else if (GetLabel.toString().equals("Scheduled Transactions")) {
   	     	Intent intent = new Intent(SmartlisterHome.this, ScheduleList.class);
   	     	startActivity(intent);             
    	}
    }
    /*
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
       		    	JSONObject jsonResponse = new JSONObject(response);
           			TextView textView2 = (TextView) findViewById(R.id.textView2);
					textView2.setText(jsonResponse.getString("alias"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					Log.v("ERROR",e.toString());
					Log.v("ERROR-MESSAGE",e.getMessage());
				}
       				Log.v("RECEIVED", response);
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
    }*/
    	 /*
    	 client.get("http://www.marcstreeter.com/sl",new AsyncHttpResponseHandler() {
    	     @Override
    	     public void onStart() {
    	         // Initiated the request
    	     }

    	     @Override
    	     public void onSuccess(String response) {
    	         Log.v("RESPONSE", response);
    	     }

    	     @Override
    	     public void onFailure(int statusCode, org.apache.http.Header[] headers, byte[] responseBody, Throwable error)
    	     {
    	         Log.v("RESPONSE",new Integer(statusCode).toString() + headers.toString() + responseBody.toString());
    	     }

    	     @Override
    	     public void onRetry() {
    	         // Request was retried
    	     }

    	     @Override
    	     public void onProgress(int bytesWritten, int totalSize) {
    	         // Progress notification
    	     }

    	     @Override
    	     public void onFinish() {
    	         // Completed the request (either success or failure)
    	     }
    	 } );
    	 */
    	//BaseJsonHttpResponseHandler jsonresp = new BaseJsonHttpResponseHandler<JSON_TYPE>();
    	/*
    	String jsonLogonString = "{\"username\": \"" + username +"\",\"password\":\""+ password+"\"}";
    	TextView tv = (TextView) findViewById(R.id.textView2);
    	try{
    	tv.setText("Sent : " + jsonLogonString);
    	HttpURLConnection httpcon = (HttpURLConnection) ((new URL("http://www.marcstreeter.com/sl/logon").openConnection()));
    	httpcon.setDoOutput(true);
    	httpcon.setRequestProperty("Content-Type", "application/json");
    	httpcon.setRequestProperty("Accept", "application/json");
    	httpcon.setRequestMethod("POST");
    	httpcon.connect();
    	byte[] outputBytes = jsonLogonString.getBytes("UTF-8");
    	OutputStream os = httpcon.getOutputStream();
    	os.write(outputBytes);
    	os.close();
    	}catch(NetworkOnMainThreadException e){
    		Log.v("Poop", "Something Else!");
			Log.v("HERE",e.toString());
			Log.v("HERE",e.getMessage());
    	}catch(Exception e){
    		Log.v("Poop", "Something Totally Else!");
			Log.v("HERE",e.toString());
			Log.v("HERE",e.getMessage());
    	}
    	*/
}
