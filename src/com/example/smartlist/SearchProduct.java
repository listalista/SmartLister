package com.example.smartlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SearchProduct extends Activity implements OnClickListener {
	
	/*Default Method which will run first*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buyer_search);  /*Retrieving the contents of the layout buyer_search*/
    
        /*Delete button in Delete Confirmation page is declared for onClickListener*/
        Button bsp = (Button) findViewById(R.id.searchproduct);
        bsp.setOnClickListener(this);

    }


    /*Executing the onClick listener method when a button is clicked*/
    @Override
    public void onClick(View v) {	
		if(v.getId() == R.id.searchproduct) {  /*Calling the refined_list when Submit is clicked*/
			startActivity(new Intent(SearchProduct.this, RefinedProdList.class)); /*New Intent is called*/
		}
    }

}

