package model;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class Location {
	
	public Location() {}
	
	public Location(double lati, double longi) {
		latitude = lati;
		longitude = longi;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitute(double latitute) {
		this.latitude = latitute;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	private double latitude;
	private double longitude;
	
}
