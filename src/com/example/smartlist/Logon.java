package com.example.smartlist;
/*
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.zip.GZIPInputStream;

import android.os.AsyncTask;
import android.os.NetworkOnMainThreadException;
import android.util.Log;
*/
public class Logon /*extends AsyncTask<HashMap<String,String>, Void, String>*/{/*
	private boolean authenticated;
	@Override
	protected String doInBackground(HashMap<String, String>... params){
		//CookieHandler.setDefault( new CookieManager( null, CookiePolicy.ACCEPT_ALL ) );
		try{
			 this.connect(params[0].get("usernameEmail"), params[0].get("passwordEmail"));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Log.v("Poop", "MALFORMEDURLEXCEPTION");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Log.v("Poop", "IOEXCEPTION");
		}
		return "fornow";
	}
	public boolean isAuthenticated(){
		return this.authenticated;
	}
	private void connect(String username, String password) throws MalformedURLException, IOException{
		CookieManager cookieManager = new CookieManager();
		CookieHandler.setDefault(cookieManager);
		//ref: http://czheng035.wordpress.com/2012/06/18/cookie-management-in-android-webview-development/
		//ref: http://stackoverflow.com/questions/14860087/should-httpurlconnection-with-cookiemanager-automatically-handle-session-cookies
		//ref: http://stackoverflow.com/questions/18057624/two-way-sync-for-cookies-between-httpurlconnection-java-net-cookiemanager-and
		//ref: http://developer.android.com/reference/java/net/HttpURLConnection.html
		HttpURLConnection httpcon = (HttpURLConnection) ((new URL("http://www.marcstreeter.com/sl/logon").openConnection()));
	    
		String jsonLogonString = "{\"username\": \"" + username +"\",\"password\":\""+ password+"\"}";
		// SET HTTP HEADERS and PROPERTIES 
		//if (cookie != null) { httpcon.setRequestProperty("Cookie", cookie);Log.v("COOKIE-SET",cookie);} // SESSION boot-up
		httpcon.setDoOutput(true); // for POST or PUT requests that have body
		httpcon.setRequestProperty("Content-Type", "application/json");
		httpcon.setRequestProperty("Accept", "application/json");
		httpcon.setRequestMethod("PUT");
		// CONNECTING 
		Log.v("STATUS","connecting");
		try{
    		httpcon.connect();
    		//SESSION update
//    		List<String> cookieList = httpcon.getHeaderFields().get("Set-Cookie");
//    	    if (cookieList != null) {
//    	        for (String cookieTemp : cookieList) {
//    	            cookieManager.setCookie(httpcon.getURL().toString(), cookieTemp);
//    	            Log.v("COOKIE-SET",cookieTemp);
//    	        }
//    	    }
    		byte[] outputBytes = jsonLogonString.getBytes("UTF-8");//format for streaming
    		OutputStream out = httpcon.getOutputStream();//open output stream
    		out.write(outputBytes);//send data via stream
    		//http://developer.android.com/reference/java/net/HttpURLConnection.html  <--- important reference
    		//http://stackoverflow.com/questions/15678208/making-put-request-with-json-data-using-httpurlconnection-is-not-working
    		//http://stackoverflow.com/questions/17208336/getting-java-io-eofexception-using-httpurlconnection  not tried
    		//http://www.coderanch.com/t/374637/java/java/Compressing-Decompressing-strings-Java not helpful in this instance
    		//http://stackoverflow.com/questions/8398277/which-encoding-does-process-getinputstream-use similar question
    		//http://stackoverflow.com/questions/4633048/httpurlconnection-reading-response-content-on-403-error infinity's answer!
    		BufferedReader in = new BufferedReader(new InputStreamReader(httpcon.getInputStream(), "UTF-8"));
    	    String f = in.readLine();
    		Log.v("RESPONSE","GOT THIS-->" + f);
    	    in.close();
    	}catch(NetworkOnMainThreadException e){
    		Log.v("CAUGHT", "NetworkOnMainThreadException");
			Log.v("ERROR",e.toString());
			Log.v("ERROR-MESSAGE",e.getMessage());
    	}catch(java.io.FileNotFoundException e){
    		Log.v("CAUGHT","FileNotFoundException");
    	}catch(Exception e){
    		Log.v("CAUGHT", "Exception");
			Log.v("ERROR",e.toString());
			Log.v("ERROR-MESSAGE",e.getMessage());
    	}finally{
    		httpcon.disconnect();
    	}
    }*/
}
