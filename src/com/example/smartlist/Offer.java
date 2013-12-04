package com.example.smartlist;


import java.text.SimpleDateFormat;
import java.util.Date;



public class Offer {
	private boolean accepted;
	private boolean cancelled;
	private String comment;
	private Offer counterOffer;
	private Date created;
	private int expiresInDays;
	private int fee;
	private Listing listing;
	private String message;
	private Date modified;
	private int offerid;
	private Offer prevOffer;
	private boolean viewed;
	public Offer(boolean a, boolean cxl, String cmt, Offer cO, Date cr, int eID, int f, Listing l, String m, Date mo, Offer pO, int oI, boolean v){
		setAccepted(a);
		setCancelled(cxl);
		setComment(cmt);
		setCounterOffer(cO);
		setCreated(cr);
		setExpiresInDays(eID);
		setFee(f);
		setListing(l);
		setMessage(m);
		setPrevOffer(pO);
		setViewed(v);
		setId(oI);
	}
	public int getId(){return offerid;
	}
	public void setId(int newId){offerid = newId;
	}
	public boolean isAccepted(){ return this.accepted;
	}
	public boolean isCancelled() {
		return cancelled;
	}
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public void setAccepted(Boolean ind){ this.accepted = ind;
	}
	public Offer getCounterOffer(){ return this.counterOffer;
	}
	public void setCounterOffer(Offer offer){this.counterOffer = offer;
	}
	public Date getCreated(){ return created;
	}
	public void setCreated(String dateString){
		try{
			Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateString); // for format 2011-01-18 00:00:00.0
			this.created = date;
		}catch(Exception e){
			this.created = new Date();
		}
	}
	public void setCreated(Date created) { this.created = created;
	}
	public int getExpiresInDays() { return expiresInDays;
	}
	public void setExpiresInDays(int expiresInDays) {this.expiresInDays = expiresInDays;
	}
	public int getFee() { return fee;
	}
	public void setFee(int fee) { this.fee = fee;
	}
	public Listing getListing() { return listing;
	}
	public void setListing(Listing listing) { this.listing = listing;
	}
	public String getMessage() { return message;
	}
	public void setMessage(String message) { this.message = message;
	}
	public Date getModified(){ return modified; // http://developer.android.com/reference/java/text/DateFormat.html
	}
	public void setModified(String dateString){
		try{
			//dateString should be in format "2011-01-18 00:00:00.0";
			// http://stackoverflow.com/questions/4772425/format-date-in-java
			// http://developer.android.com/reference/java/text/SimpleDateFormat.html
			//Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(dateString);
			Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateString); // for format 2011-01-18 00:00:00.0
			this.modified = date;
		}catch(Exception e){
			this.modified = new Date();
		}
	}
	public void setModified(Date modified) { this.modified = modified;
	}
	public Offer getPrevOffer() { return prevOffer;
	}
	public void setPrevOffer(Offer prevOffer) { this.prevOffer = prevOffer;
	}
	public boolean isViewed() { return viewed;
	}
	public void setViewed(boolean viewed) { this.viewed = viewed;
	}
}
