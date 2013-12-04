package com.example.smartlist;


import java.text.SimpleDateFormat;
import java.util.Date;



public class Offer {
	private Boolean accepted;
	private Offer counterOffer;
	private Date created;
	private Integer expiresInDays;
	private Integer fee;
	private Listing listing;
	private String message;
	private Date modified;
	private Integer offerid;
	private Offer prevOffer;
	private Boolean viewed;
	public Offer(Boolean a, Offer cO, Date cr, Integer eID, Integer f, Listing l, String m, Date mo, Offer pO, Integer oI, Boolean v){
		setAccepted(a);
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
	public Integer getId(){return offerid;
	}
	public void setId(Integer newId){offerid = newId;
	}
	public boolean isAccepted(){ return this.accepted;
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
	public Integer getExpiresInDays() { return expiresInDays;
	}
	public void setExpiresInDays(Integer expiresInDays) {this.expiresInDays = expiresInDays;
	}
	public Integer getFee() { return fee;
	}
	public void setFee(Integer fee) { this.fee = fee;
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
	public void setViewed(Boolean viewed) { this.viewed = viewed;
	}
}
