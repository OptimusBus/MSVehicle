package model;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class Vehicle {
	
	public Vehicle() {}
	
	public Vehicle(String id) {
		this.vehicleId = id;
	}
	
	public Vehicle(String id, String sp, boolean isActive) {
		this.vehicleId = id;
		this.standingPoint = sp;
		this.isActive = isActive;
	}
	
	public String getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public String getStandingPoint() {
		return standingPoint;
	}
	public void setStandingPoint(String standingPoint) {
		this.standingPoint = standingPoint;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public int getMaxOccupancy() {
		return maxOccupancy;
	}
	public void setMaxOccupancy(int maxOccupancy) {
		this.maxOccupancy = maxOccupancy;
	}
	public int getCurrentOccupancy() {
		return currentOccupancy;
	}
	public void setCurrentOccupancy(int currentOccupancy) {
		this.currentOccupancy = currentOccupancy;
	}
	public String getVehicleToSupport() {
		return vehicleToSupport;
	}
	public void setVehicleToSupport(String vehicleToSupport) {
		this.vehicleToSupport = vehicleToSupport;
	}
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	public Route getRoute() {
		return route;
	}
	public void setRoute(Route route) {
		this.route = route;
	}
	public void addPassenger() {
		if(currentOccupancy < maxOccupancy) {
			this.currentOccupancy++;
		}
	}
	public void removePassenger() {
		if(currentOccupancy > 0) {
			this.currentOccupancy--;
		}
	}
	
	private String vehicleId;
	private Location location;
	private String standingPoint;
	public static enum State { FREE, PRINCIPALE, SUPPORTO, FUORISERVIZIO }
	private boolean isActive = false;
	private int maxOccupancy = 7;
	private int currentOccupancy = 0;
	private String vehicleToSupport;
	private State state = State.FREE;
	private Route route;
}
