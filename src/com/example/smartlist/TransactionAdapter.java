package com.example.smartlist;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TransactionAdapter extends ArrayAdapter<Transaction>{
	 // references to our images
    private Integer[] mThumbIds = {
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7
    };
	List<Transaction> items;
	public TransactionAdapter(Context context,List<Transaction> items) {
		super(context, 0, items);
		this.items = items;
	}
	@Override
	public int getCount() {
        return items.size();
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
        Transaction item = items.get(position);
        if(item != null){
        	tv.setText(item.getOffer().getListing().getTitle());
        }
        /*if(item instanceof ListingForSale){
        	((ListingForSale) item).getFee();
        }*/
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
	@Override
	public long getItemId(int position) {
		return getItem(position).getId();
	}
	@Override
	public Transaction getItem(int position) {
        return items.get(position);
	}
}
