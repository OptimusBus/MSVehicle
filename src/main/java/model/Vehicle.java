package model;

import java.util.List;

public class Vehicle {
	
	public Vehicle(String id, Location loc, String sp, boolean isActive) {
		this.vehicleId = id;
		this.location = loc;
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
	public List<Node> getRoute() {
		return route;
	}
	public void setRoute(List<Node> route) {
		this.route = route;
	}

	private String vehicleId;
	private Location location;
	private String standingPoint;
	private static enum State { FREE, PRINCIPALE, SUPPORTO, FUORISERVIZIO }
	private boolean isActive = false;
	private int maxOccupancy = 7;
	private int currentOccupancy = 0;
	private String vehicleToSupport;
	private State state = State.FREE;
	private List<Node> route;
}
