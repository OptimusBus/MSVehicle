package db;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
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
		Document vehicle = Vehicle.encodeVehicle(v);
		MongoDatabase db = m.getDatabase("VehiclesDB");
		MongoCollection<Document> coll = db.getCollection("vehicles");
		coll.insertOne(vehicle);
	}
	
	public void updateVehicle(Vehicle v) {
		Document vehicle = Vehicle.encodeVehicle(v);
		MongoDatabase db = m.getDatabase("VehiclesDB");
		MongoCollection<Document> coll = db.getCollection("vehicles");
		coll.replaceOne(Filters.eq("vehicleId", v.getVehicleId()), vehicle);
	}
	
	public void removeVehicle(String vehicleId) throws Exception {
		MongoDatabase db = m.getDatabase("VehiclesDB");
		MongoCollection<Document> coll = db.getCollection("vehicles");
		coll.deleteOne(Filters.eq("vehicleId", vehicleId));
	}
	
	private static final MongoClient m = new MongoClient("172.18.10.144", 31181);
}
