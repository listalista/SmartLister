package com.example.smartlist;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Schedule {
	Date created;
	Date dateTime;
	String description;
	double lon;
	double lat;
	Date modified;
	public Schedule(Date cr, Date dT, String d, double lon, double lat, Date mo){
		setCreated(cr);
		setDateTime(dT);
		setDescription(d);
		setLon(lon);
		setLat(lat);
		setModified(mo);
	}
	public String getDescription() { return description;
	}
	public void setDescription(String description) { this.description = description;
	}
	public double getLon() { return lon;
	}
	public void setLon(double lon) { this.lon = lon;
	}
	public double getLat() { return lat;
	}
	public void setLat(double lat) { this.lat = lat;
	}
	public Date getDateTime() { return dateTime;
	}
	public void setDateTime(Date time) { this.dateTime = time;
	}
	public Date getModified() { return modified;
	}
	public void setModified(Date modified) { this.modified = modified;
	}
	public void setModified(String dateString){
		try{
			Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateString); // for format 2011-01-18 00:00:00.0
			this.modified = date;
		}catch(Exception e){
			this.modified = new Date();
		}
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
