package model;

import javax.xml.bind.annotation.XmlRootElement;

import org.bson.Document;


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
	public static Location decodeLocation(Document dd) {
		Document d = (Document) dd.get("location");
		if(d.getDouble("latitude") == null || d.getDouble("longitude") == null) return null;
		double lati = d.getDouble("latitude");
		double longi = d.getDouble("longitude");
		return new Location(lati, longi);
	}
	public static Document encodeLocation(Location l) {
		Document d = new Document();
		if(l.getLatitude() == -1 || l.getLongitude() == -1) {
			d.append("location", new Document());
		}else {
			d.append("location", new Document("longitude", l.getLongitude()).append("latitude", l.getLatitude()));
		}
		return d;
	}
	
	private double latitude = -1;
	private double longitude = -1;
	
}
