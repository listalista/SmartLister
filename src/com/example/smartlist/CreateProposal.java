package com.example.smartlist;

import java.io.UnsupportedEncodingException;

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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;

public class CreateProposal extends FragmentActivity {
	
	public static final String USER_PREFS = "UserPrefs";
	public static final String P_LATITUDE = "lat";
	public static final String P_LONGITUDE = "lon";
	public static final String LOCATION = "location";
	public static final String LAT_LON_TS = "lat_lon_ts";
	public static final String OBO_IND = "obo_ind";
	public static final String P_RADIUS = "radius";
	public static final String P_LANG = "lang";
	public static final String P_CURRENCY = "currency";
	public static final String FEE = "fee";
	public static final String DESCR = "description";
	public static final String TITLE = "title";
	public static final String CATEG = "category";
	public static final String AUTHORIZED = "authenticated";
	private SharedPreferences prefs;
	private SharedPreferences.Editor prefsEditor;
	private PersistentCookieStore myCookieStore;

}
