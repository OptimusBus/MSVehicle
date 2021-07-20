package service;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import db.MongoConnector;
import model.Location;
import model.Node;
import model.Route;
import model.Vehicle;


public class Branch implements BranchLocal {
	
	MongoConnector em = new MongoConnector();
	
	public Branch() {}
	
	public Location encodeLocation(Document loc) {
		return new Location(Double.parseDouble(loc.getString("latitude")), Double.parseDouble(loc.getString("longitude")));
	}
	
	public Node encodeNode(Document node) {
		Node n = new Node();
		n.setNodeId(node.getString("nodeId"));
		n.setPotentialDemand(Double.parseDouble(node.getString("potentialDemand")));
		if(node.getString("type").equalsIgnoreCase("PICKUPPOINT")) n.setType(Node.Type.PICKUPPOINT);
		else if(node.getString("type").equalsIgnoreCase("STANDINGPOINT")) n.setType(Node.Type.STANDINGPOINT);
		else n.setType(Node.Type.TRANSITION);
		Document loc = (Document) node.get("location");
		n.setLocation(encodeLocation(loc));
		return n;
	}
	
	
	public Route encodeRoute(Document route) {
		Route r = new Route();
		List<Node> n = new ArrayList<Node>();
		for(int i = 0; i < route.size(); i++) {
			Document d = (Document) route.get("node"+i);
			n.add(encodeNode(d));
		}
		return r;
	}
	
	
	public Vehicle encodeVehicle(Document d) {
		Vehicle v = new Vehicle();
		if(d.getString("isActive").equals("true")) v.setActive(true);
		else v.setActive(false); // isActive
		v.setCurrentOccupancy(Integer.parseInt(d.getString("currentOccupancy"))); // currentOccupancy
		v.setMaxOccupancy(Integer.parseInt(d.getString("maxOccupancy"))); // maxOccupancy
		Document loc = (Document) d.get("location");		
		v.setLocation(encodeLocation(loc)); // location
		if(d.getString("state").equals("FREE")) v.setState(Vehicle.State.FREE);
		if(d.getString("state").equals("SUPPORTO")) v.setState(Vehicle.State.SUPPORTO);
		if(d.getString("state").equals("PRINCIPALE")) v.setState(Vehicle.State.PRINCIPALE);
		if(d.getString("state").equals("FUORISERVIZIO")) v.setState(Vehicle.State.FUORISERVIZIO); // state
		v.setStandingPoint(d.getString("standingPoint")); // standingPoint
		v.setVehicleId(d.getString("vehicleId")); // vehicleId
		v.setVehicleToSupport(d.getString("vehicleToSupport")); // vehicleToSupport
		if(d.getString("route").equalsIgnoreCase("null")) v.setRoute(null);
		else {
			Document route = (Document) d.get("route");
			v.setRoute(encodeRoute(route));
		}
		return v;
	}
	
	@Override
	public void updateVehicleRoute(String id, Route route) {
		Document v = em.getVehicleById(id);	
		Vehicle ve = encodeVehicle(v);
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
		return encodeVehicle(v);
	}
	
	@Override
	public List<Vehicle> getAllVehicles() {
		List<Document> ld = em.getAllVehicles();
		List<Vehicle> lv = new ArrayList<Vehicle>();
		for(Document d : ld) {
			lv.add(encodeVehicle(d));
		}
		return lv;
	}

	@Override
	public void assignSupport(String vehicleId, String idToSupport) {
		Vehicle v = encodeVehicle(em.getVehicleById(vehicleId));
		v.setVehicleToSupport(idToSupport);
		em.updateVehicle(v);
	}

	@Override
	public void updateVehicleState(String vehicleId, int s) {
		Vehicle v = encodeVehicle(em.getVehicleById(vehicleId));
		if(s == 0) v.setState(Vehicle.State.FREE);
		if(s == 1) v.setState(Vehicle.State.PRINCIPALE);
		if(s == 2) v.setState(Vehicle.State.SUPPORTO);
		if(s == 3) v.setState(Vehicle.State.FUORISERVIZIO);
		em.updateVehicle(v);
	}

	@Override
	public void updateStandingPoint(String vehicleId, String standingPoint) {
		Vehicle v = encodeVehicle(em.getVehicleById(vehicleId));
		v.setStandingPoint(standingPoint);
		em.updateVehicle(v);
	}

	@Override
	public void setLastKnownPosition(String vehicleId, Location location) {
		Vehicle v = encodeVehicle(em.getVehicleById(vehicleId));
		v.setLocation(location);
		em.updateVehicle(v);
	}

	@Override
	public void activateVehicle(String vehicleId) {
		Vehicle v = encodeVehicle(em.getVehicleById(vehicleId));
		v.setActive(true);
		em.updateVehicle(v);
	}

	@Override
	public void addPassenger(String vehicleId) {
		Vehicle v = encodeVehicle(em.getVehicleById(vehicleId));
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

}
