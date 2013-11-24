package com.example.smartlist;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListingAdapter extends ArrayAdapter<Listing>{
	List<Listing> items;
    public ListingAdapter(Context context, List<Listing> listings){
    	super(context, 0,listings);
    	this.items = listings;
    }

	@Override
	public int getCount() {
        return this.items.size();
	}

	@Override
	public ListingForSale getItem(int position) {
        return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//ImageView imageView;
		View listingView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
        	 LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
             listingView = li.inflate(R.layout.listing_tile, null);
            //imageView = new ImageView(mContext);
            //imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            //imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            //imageView.setPadding(8, 8, 8, 8);
        } else {
        	listingView = (View) convertView;
            //imageView = (ImageView) convertView;
        }
        TextView tv = (TextView)listingView.findViewById(R.id.tileText);
        Listing item = items.get(position);
        if(item != null){
        	tv.setText(item.toString());
        }
        ImageView iv = (ImageView)listingView.findViewById(R.id.tileImage);
        int max = mThumbIds.length;
        int index = (int)(Math.floor(Math.random() * max));
        iv.setImageResource(mThumbIds[index]);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        //iv.setLayoutParams(new GridView.LayoutParams(85, 85));
        iv.setPadding(8, 8, 8, 8);
        //imageView.setImageResource(mThumbIds[position]);
        //return imageView;
        return listingView;
	}

    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7
    };

}
