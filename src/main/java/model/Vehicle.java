package model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity implementation class for Entity: Vehicle
 *
 */
@Entity
@NamedQueries ({
	@NamedQuery(name="Vehicle.getAllVehicles", query = "SELECT * from Vehicles"),
	@NamedQuery(name="Vehicle.getVehicleById", query = "SELECT * from Vehicles where vehcileId = :id"),
	@NamedQuery(name="Vehicle.getVehiclesByState", query = "SELECT * from Vehicles where state = :state"),
	@NamedQuery(name="Vehicle.getNotEmptyVehicles", query = "SELECT * from Vehicles where currentOccupancy < maxOccupancy"),
	@NamedQuery(name="Vehicle.countVehicles", query = "SELECT count(*) from Vehicles"),
})

@XmlRootElement
public class Vehicle {
	
	public Vehicle(String id) {
		this.vehicleId = id;
	}
	
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
	public Route getRoute() {
		return route;
	}
	public void setRoute(Route route) {
		this.route = route;
	}
	public void addPassenger(String p) {
		if(currentOccupancy < maxOccupancy) {
			this.passengers.add(p);
			this.currentOccupancy++;
		}
	}
	public void removePassenger(String p) {
		if(currentOccupancy > 0) {
			int i = 0;
			boolean done = false;
			while(!done && i < this.passengers.size()) {
				if(this.passengers.get(i).equals(p)) {
					this.passengers.remove(i);
					done = true;
				}
				i++;
			}
		}
	}
	
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id private String vehicleId;
	private Location location;
	private String standingPoint;
	public static enum State { FREE, PRINCIPALE, SUPPORTO, FUORISERVIZIO }
	private boolean isActive = false;
	private int maxOccupancy = 7;
	private int currentOccupancy = 0;
	private String vehicleToSupport;
	private State state = State.FREE;
	private Route route;
	private List<String> passengers;
}
