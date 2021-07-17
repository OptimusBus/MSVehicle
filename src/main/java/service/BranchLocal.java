package service;

import java.util.List;

import javax.ejb.Local;

import model.Location;
import model.Route;
import model.Vehicle;

@Local
public interface BranchLocal {
	public void updateVehicleRoute(String id, Route route);
	public String createVehicle(String vehicleId);
	public Vehicle getVehicle(String vehicleId); 
	public List<Vehicle> getAllVehicles();
	public void assignSupport(String vehicleId, String idToSupport);
	public void updateVehicleState(String vehicleId, int s);
	public void updateStandingPoint(String vehicleId, String standingPoint);
	public void setLastKnownPosition(String vehicleId, Location location);
	public void activateVehicle(String vehicleId);
	public void addPassenger(String vehicleId, String passengerId);
	
}
