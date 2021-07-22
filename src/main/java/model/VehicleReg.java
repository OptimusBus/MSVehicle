package model;

import javax.xml.bind.annotation.XmlRootElement;

import org.bson.Document;

@XmlRootElement
public class VehicleReg {	
	public VehicleReg() {}
	public VehicleReg(String username, String password, String email) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public static VehicleReg decodeVehicleReg(Document d) {
		if(d.size() == 0) return null;
		String us = d.getString("username");
		String pwd = d.getString("password");
		String email = d.getString("email");
		return new VehicleReg(us, pwd, email);
	}
	
	public static Document encodeVehicleReg(VehicleReg vr) {
		Document d = new Document("username", vr.getUsername());
		d.append("password", vr.getPassword());
		d.append("email", vr.getEmail());
		return d;
	}

	private String username;
	private String password;
	private String email;
}
