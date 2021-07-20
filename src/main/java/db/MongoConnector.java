package db;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import model.Node;
import model.Route;
import model.Vehicle;


public class MongoConnector {
	
	public MongoConnector() {}
		
	public static void close() {
		m.close();
	}
	
	public List<Document> getAllVehicles(){
		MongoDatabase db = m.getDatabase("VehiclesDB");
		MongoCollection<Document> coll = db.getCollection("vehicles");
		return coll.find().into(new ArrayList<Document>());
	}
	
	public Document getVehicleById(String vehicleId){
		MongoDatabase db = m.getDatabase("VehiclesDB");
		MongoCollection<Document> coll = db.getCollection("vehicles");
		return coll.find(Filters.eq("vehicleId", vehicleId)).first();
	}
	
	public List<Document> getVehiclesByState(String state){
		MongoDatabase db = m.getDatabase("VehiclesDB");
		MongoCollection<Document> coll = db.getCollection("vehicles");
		return coll.find(Filters.eq("state", state)).into(new ArrayList<Document>());
	}
	
	public List<Document> getUnfullVehicles(String state){
		MongoDatabase db = m.getDatabase("VehiclesDB");
		MongoCollection<Document> coll = db.getCollection("vehicles");
		return coll.find(Filters.lt("currentOccupancy", 7)).into(new ArrayList<Document>());
	}
	
	public long getVehiclesCount() {
		MongoDatabase db = m.getDatabase("VehiclesDB");
		MongoCollection<Document> coll = db.getCollection("vehicles");
		return coll.count();
	}
	
	public void persist(Vehicle v) {
		Document vehicle = new Document("vehicleId", v.getVehicleId())
				.append("location", new Document("longitude", v.getLocation().getLongitude()).append("latitude", v.getLocation().getLatitude()))
                .append("standingPoint", v.getStandingPoint())
                .append("state", v.getState().toString())
                .append("isActive", v.isActive())
                .append("route", this.encodeRoute(v.getRoute()))
                .append("maxOccupancy", v.getMaxOccupancy())
                .append("currentOccupancy", v.getCurrentOccupancy())
                .append("vehicleToSupport", v.getVehicleToSupport());
		MongoDatabase db = m.getDatabase("VehiclesDB");
		MongoCollection<Document> coll = db.getCollection("vehicles");
		coll.insertOne(vehicle);
	}
	
	public void updateVehicle(Vehicle v) {
		Document vehicle = new Document("vehicleId", v.getVehicleId())
				.append("location", new Document("longitude", v.getLocation().getLongitude()).append("latitude", v.getLocation().getLatitude()))
                .append("standingPoint", v.getStandingPoint())
                .append("state", v.getState().toString())
                .append("isActive", v.isActive())
                .append("route", this.encodeRoute(v.getRoute()))
                .append("maxOccupancy", v.getMaxOccupancy())
                .append("currentOccupancy", v.getCurrentOccupancy())
                .append("vehicleToSupport", v.getVehicleToSupport());
		MongoDatabase db = m.getDatabase("VehiclesDB");
		MongoCollection<Document> coll = db.getCollection("vehicles");
		coll.updateOne(Filters.eq("vehicleId", v.getVehicleId()), vehicle);
	}
	
	public Document encodeRoute(Route r) {
		if(r.getRoute().size() == 0) return null;
		Document route = new Document();
		int i = 0;
		for(Node n : r.getRoute()) {
			route.append("node"+i, new Document("location", new Document("longitude", n.getLocation().getLongitude())
															.append("latitude", n.getLocation().getLatitude()))
									.append("nodeId", n.getNodeId())
									.append("type", n.getType().toString())
									.append("potentialDemand", n.getPotentialDemand()));
			i++;
		}
		return route;
	}
	
	private static final MongoClient m = new MongoClient("172.18.10.144", 31181);
}
