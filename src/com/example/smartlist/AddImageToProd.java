package com.example.smartlist;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class AddImageToProd extends Activity {
	
	ImageView iv;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.capture_image);
        
        iv = (ImageView) findViewById(R.id.capturedimage);
        ImageButton btn = (ImageButton) findViewById(R.id.gotocamera);
        btn.setOnClickListener(new OnClickListener() {
        	
        	@Override
        	public void onClick(View view) {
        		Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        		startActivityForResult(intent, 0);
        	}
        });
        
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if(requestCode == 0) {
    		Bitmap theImage = (Bitmap) data.getExtras().get("data");
    		iv.setImageBitmap(theImage);
    	}
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.go_home, menu);
        return true;
    }
    
    /* Performing action upon selecting a Menu Item */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      switch(item.getItemId()) {
        case R.id.action_go_home:
            startActivity(new Intent(AddImageToProd.this, SmartlisterHome.class));
            finish();
            break;
        default:
            return super.onOptionsItemSelected(item);
      }
      return true;
    }
}
