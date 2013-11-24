package com.example.smartlist;


public abstract class Listing {
	private String category;
	private String creationTimeStamp;
	private String description;
	private double locLat;
	private double locLon;
	private int radius;
	private String title;
	
	public Listing( String category, String creationTimeStamp, String description, double locLat,double locLon,int radius,String title){
		setCategory(category);
		setCreationTimeStamp(creationTimeStamp);
		setDescription(description);
		setLocLat(locLat);
		setLocLon(locLon);
		setRadius(radius);
		setTitle(title);
    }
	//setters
	public void setCategory(String category){ this.category = category;}
	public String getCategory(){ return this.category;}
	public void setCreationTimeStamp(String creationTimeStamp){ this.creationTimeStamp = creationTimeStamp;}
	public String getCreationTimeStamp(){ return this.creationTimeStamp;}
	public void setDescription(String description){ this.description = description;}
	public String getDescription(){ return this.description;}
	public void setLocLat(double locLat){this.locLat = locLat;}
	public double getLocLat(){ return this.locLat;}
	public void setLocLon(double locLon){this.locLon = locLon;}
	public double getLocLon(){ return this.locLon;}
	public void setRadius(int radius){this.radius = radius;}
	public int getRadius(){ return this.radius;}
	public void setTitle(String title){ this.title = title;}
	public String getTitle(){ return this.title;}
}
