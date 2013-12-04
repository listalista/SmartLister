package com.example.smartlist;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {

	boolean cancelled;
	Date created;
	int expires_in_days;
	String description;
	Offer offer;
	Schedule schedule;
	public Transaction(boolean cxl, Date cr, int eID, String d, Offer o, Schedule s){
		setCancelled(cxl);
		setCreated(cr);
		setDescription(d);
		setOffer(o);
		setSchedule(s);
	}
	public Offer getOffer() { return offer;
	}
	public void setOffer(Offer offer) { this.offer = offer;
	}
	public Schedule getSchedule() { return schedule;
	}
	public void setSchedule(Schedule schedule) { this.schedule = schedule;
	}
	public String getDescription() { return description;
	}
	public void setDescription(String description) { this.description = description;
	}
	public int getExpires_in_days() { return expires_in_days;
	}
	public void setExpires_in_days(int expires_in_days) { this.expires_in_days = expires_in_days;
	}
	public boolean isCancelled() { return cancelled;
	}
	public void setCancelled(boolean cancelled) { this.cancelled = cancelled;
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
}
