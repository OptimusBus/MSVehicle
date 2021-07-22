package service;

import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import db.MongoConnector;
import model.Location;
import model.Route;
import model.Vehicle;


public class Branch implements BranchLocal {
	
	MongoConnector em = new MongoConnector();
	
	public Branch() {}
	
	@Override
	public void updateVehicleRoute(String id, Route route) {
		Document v = em.getVehicleById(id);	
		Vehicle ve = Vehicle.decodeVehicle(v);
		ve.setRoute(route);
		em.updateVehicle(ve);
	}
	
	@Override
	public String createVehicle(String vehicleId) {
		Vehicle v = new Vehicle(vehicleId);
		em.persist(v);
		return vehicleId;
	}
	
	@Override
	public Vehicle getVehicle(String vehicleId) {
		Document v = em.getVehicleById(vehicleId);
		if(v == null) return null;
		return Vehicle.decodeVehicle(v);
	}
	
	@Override
	public List<Vehicle> getAllVehicles() {
		List<Document> ld = em.getAllVehicles();
		List<Vehicle> lv = new ArrayList<Vehicle>();
		for(Document d : ld) {
			lv.add(Vehicle.decodeVehicle(d));
		}
		return lv;
	}

	@Override
	public void assignSupport(String vehicleId, String idToSupport) {
		Vehicle v = Vehicle.decodeVehicle(em.getVehicleById(vehicleId));
		v.setVehicleToSupport(idToSupport);
		em.updateVehicle(v);
	}

	@Override
	public void updateVehicleState(String vehicleId, String s) {
		Vehicle v = Vehicle.decodeVehicle(em.getVehicleById(vehicleId));
		Vehicle.State st = Vehicle.State.FREE;
		if(s.equalsIgnoreCase("PRINCIPALE")) st = Vehicle.State.PRINCIPALE;
		if(s.equalsIgnoreCase("SUPPORTO")) st = Vehicle.State.SUPPORTO;
		if(s.equalsIgnoreCase("FUORISERVIZIO")) st = Vehicle.State.FUORISERVIZIO;
		v.setState(st);
		em.updateVehicle(v);
	}

	@Override
	public void updateStandingPoint(String vehicleId, String standingPoint) {
		Vehicle v = Vehicle.decodeVehicle(em.getVehicleById(vehicleId));
		v.setStandingPoint(standingPoint);
		em.updateVehicle(v);
	}

	@Override
	public void setLastKnownPosition(String vehicleId, Location location) {
		Vehicle v = Vehicle.decodeVehicle(em.getVehicleById(vehicleId));
		v.setLocation(location);
		em.updateVehicle(v);
	}

	@Override
	public void activateVehicle(String vehicleId) {
		Vehicle v = Vehicle.decodeVehicle(em.getVehicleById(vehicleId));
		v.setActive(true);
		em.updateVehicle(v);
	}

	@Override
	public void addPassenger(String vehicleId) {
		Vehicle v = Vehicle.decodeVehicle(em.getVehicleById(vehicleId));
		v.addPassenger();
		em.updateVehicle(v);
	}

	@Override
	public String calcVehicleId() {
		long n = em.getVehiclesCount();
		n++;
		String a = ""+n;
		int l = a.length();
		if(l<2) return "VE000" + a; 
		else if(l<3) return "VE00" + a;
		else if(l<4) return "VE0" + a;
		return "VE" + a;
	}

	@Override
	public void removeVehicle(String vehicleId) throws Exception {
		em.removeVehicle(vehicleId);
	}

}
