package service;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.Location;
import model.Route;
import model.Vehicle;


@Stateless
public class Branch implements BranchLocal {
	
	@PersistenceContext
	EntityManager em;
	
	public Branch() {}
	
	@Override
	public void updateVehicleRoute(String id, Route route) {
		Vehicle v = em.find(Vehicle.class, id);	
		v.setRoute(route);
	}

	@Override
	public String createVehicle(String vehicleId) {
		Vehicle v = new Vehicle(vehicleId);
		em.persist(v);
		return vehicleId;
	}

	@Override
	public Vehicle getVehicle(String vehicleId) {
		return em.find(Vehicle.class, vehicleId);
	}

	@Override
	public List<Vehicle> getAllVehicles() {
		return em.createNamedQuery("Vehicle.getAllVehicles", Vehicle.class).getResultList();
	}

	@Override
	public void assignSupport(String vehicleId, String idToSupport) {
		Vehicle v = em.find(Vehicle.class, vehicleId);
		v.setVehicleToSupport(idToSupport);
	}

	@Override
	public void updateVehicleState(String vehicleId, int s) {
		Vehicle v = em.find(Vehicle.class, vehicleId);
		if(s == 0) v.setState(Vehicle.State.FREE);
		if(s == 1) v.setState(Vehicle.State.PRINCIPALE);
		if(s == 2) v.setState(Vehicle.State.SUPPORTO);
		if(s == 3) v.setState(Vehicle.State.FUORISERVIZIO);
	}

	@Override
	public void updateStandingPoint(String vehicleId, String standingPoint) {
		Vehicle v = em.find(Vehicle.class, vehicleId);
		v.setStandingPoint(standingPoint);
	}

	@Override
	public void setLastKnownPosition(String vehicleId, Location location) {
		Vehicle v = em.find(Vehicle.class, vehicleId);
		v.setLocation(location);
	}

	@Override
	public void activateVehicle(String vehicleId) {
		Vehicle v = em.find(Vehicle.class, vehicleId);
		v.setActive(true);
	}

	@Override
	public void addPassenger(String vehicleId, String passengerId) {
		Vehicle v = em.find(Vehicle.class, vehicleId);
		v.addPassenger(passengerId);
	}

}
