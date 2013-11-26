package com.example.smartlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ChatList extends Activity implements OnItemClickListener {
	
	ListView listView; /*Declaring Listview in public*/
	
	/*Default Method which will run first*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_list);  /*Retrieving the contents of the layout refined_list*/
        
        listView = (ListView) findViewById(R.id.chatlist); /*Declaring the List View*/
        
        /*Retrieving the array content from strings.xml file*/
        String[] chatlist = getResources().getStringArray(R.array.Temp_Chat_List);
    
        /*Pushing the array content into list view and capture the onClick action*/
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, chatlist));
        listView.setOnItemClickListener(this);
    }
 
    /*onClickListener action upon clicking an option from the list*/
    @Override
    public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
    	
    	
    	     Intent intent = new Intent(ChatList.this, ChatApplication.class);
    	     startActivity(intent);                  
    	
    }
}