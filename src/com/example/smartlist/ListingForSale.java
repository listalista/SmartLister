package com.example.smartlist;

public class ListingForSale extends Listing{

	private String currency;
	private int fee;
	private boolean  obo;
	
	public ListingForSale(String id, String category, String creationTimeStamp,String currency,
			String description, int fee,double locLat, double locLon, boolean obo,int radius,
			String title) {
		super(id,category, creationTimeStamp, description, locLat, locLon, radius, title);
		setCurrency(currency);
		setFee(fee);
		setObo(obo);
		
	}
	public ListingForSale(int id, String category, String creationTimeStamp,String currency,
			String description, int fee,double locLat, double locLon, boolean obo,int radius,
			String title) {
		super(id,category, creationTimeStamp, description, locLat, locLon, radius, title);
		setCurrency(currency);
		setFee(fee);
		setObo(obo);
		
	}
	public void setCurrency(String currency){ this.currency = currency;}
	public String getCurrency(){ return this.currency;}
	public void setFee(int fee){ this.fee = fee;}
	public int getFee(){ return this.fee;}
	public void setObo(boolean obo){ this.obo = obo;}
	public boolean getObo(){ return this.obo;}
	@Override
	public String toString(){
		String newString =  "("+String.valueOf(getLocLat()) +","+String.valueOf(getLocLon())+ ")" +
				  getTitle() + " - " + getDescription() + " [ " + getCurrency() + " " + String.valueOf(getFee()) + "]";
		
		return newString;
	}
}
