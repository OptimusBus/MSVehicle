package controller;


import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import org.bson.Document;
import com.mongodb.BasicDBObject;

import innerconnector.HttpConnector;
import model.Location;
import model.Vehicle;
import model.VehicleReg;
import service.Branch;
import service.BranchLocal;

@Consumes("application/json")
@Produces("application/json")
@Path("/vehicles")
public class VehicleController {
	
	private BranchLocal branch = new Branch();
	
	public VehicleController() {
		super();
	}

	@GET
	@Path("/support")
	public Response requestVehicleSupport(@QueryParam("vehicleId") String vehicleId) {
		Vehicle v = branch.getVehicle(vehicleId);
		Response res = HttpConnector.requestBestVehicle(Vehicle.encodeVehicle(v).toJson());
		if(res.getStatus() == 404) return Response.status(404).entity("Not Found").build();
		if(res.getStatus() == 200) {
			return Response.ok(res.readEntity(String.class)).build();
		}
		return Response.status(500).entity("Server Error").build();
	}
	
	@GET
	@Path("/getRoute")
	public Response requestVehicleRoute(@QueryParam("vehicleId") String vehicleId) {
		Response res = HttpConnector.requestVehicleRoute(vehicleId);
		System.out.println(res.getStatus());
		if(res.getStatus() == 404) return Response.status(404).entity("Not Found").build();
		if(res.getStatus() == 200) {
			return Response.ok(res.readEntity(String.class)).build();
		}
		return Response.status(500).entity("Server Error").build();
	}
	
	@GET
	@Path("/all")
	public Response getAllVehicles() {
		List<Vehicle> vl = new ArrayList<Vehicle>();
		vl = branch.getAllVehicles();
		if(vl == null) return Response.status(500).entity("Server Error").build();
		if(vl.size() == 0) return Response.status(404).entity("No vehicle found").build();
		return Response.ok(vl).build();
	}
	
	@GET
	@Path("/{vehicleId}")
	public Response getVehicleById(@PathParam("vehicleId") String vehicleId) {
		Vehicle v = branch.getVehicle(vehicleId);
		if(v == null) return Response.status(404).build();
		return Response.ok(v).build();
	}
	
	@GET
	@Path("/getEmail")
	public Response getVehicleByEmail(@QueryParam("email") String email) {
		Vehicle v = branch.getVehicleByEmail(email);
		if(v == null) return Response.status(404).build();
		return Response.ok(v).build();
	}
	
	@GET
	@Path("/failure")
	public Response vehicleFailureReport(@QueryParam("vehicleId") String vehicleId) {
		try {
			Vehicle v = branch.getVehicle(vehicleId);
			if(v == null) return Response.status(404).build();
			branch.updateVehicleState(vehicleId, "FUORISERVIZIO");
			return Response.status(200).build();
		}catch(Exception e) {
			return Response.status(500).build();
		}
	}
	
	@PUT
	@Path("/setSP")
	public Response bestStandingPoint(String vehicleId) {
		try {
			Vehicle v = branch.getVehicle(vehicleId);
			if(v == null) return Response.status(404).build();
			return Response.status(204).build();
		}catch(Exception e) {
			return Response.status(500).build();
		}
	}
	
	@POST
	@Path("/sendPosition")
	public Response sendPosition(String request) {
		try {
			BasicDBObject b = BasicDBObject.parse(request);
			Document d = new Document(b);
			String vehicleId = d.getString("vehicleId");
			double latitude = Double.parseDouble(d.getString("latitude"));
			double longitude = Double.parseDouble(d.getString("longitude"));
			Vehicle v = branch.getVehicle(vehicleId);
			if(v == null) return Response.status(404).build();
			branch.setLastKnownPosition(vehicleId, new Location(latitude, longitude));
			return Response.status(204).build();
		}catch(Exception e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
	}
	
	@PUT
	@Path("/endroute")
	public Response endOfRouteReport(String vehicleId) {
		try {
			Vehicle v = branch.getVehicle(vehicleId);
			if(v == null) return Response.status(404).build();
			int size = v.getRoute().getRoute().size();
			if(size == 0) throw new Exception();
			branch.setLastKnownPosition(vehicleId, v.getRoute().getRoute().get(size-1).getLocation());
			branch.updateVehicleState(vehicleId, "FREE");
			return Response.status(204).build();
		}catch(Exception e) {
			return Response.status(500).build();
		}
	}
	
	@POST
	@Path("/create")
	public Response createVehicle(String request) {
		try {
			BasicDBObject b = BasicDBObject.parse(request);
			Document d = new Document(b);
			VehicleReg vr = VehicleReg.decodeVehicleReg(d);
			String id = branch.calcVehicleId();
			vr.setVehicleId(id);
			Response res = HttpConnector.requestVehicleRegistration(VehicleReg.encodeVehicleReg(vr).toJson());
			int code = res.getStatus();
			switch(code) {
				case 201:
					id = branch.createVehicle(id);
					return Response.created(new URI("/vehicles/" + id)).build();
				case 500:
					return Response.status(code).entity("Security Service Error").build();
				default:
					return Response.status(code).build();
			}	
		} catch (Exception e) {
			return Response.status(500).build();
		}
	}
	
	@POST
	@Path("/activate")
	public Response activateVehicle(String id) {
		try {
			Vehicle v = branch.getVehicle(id);
			if(v == null) return Response.status(404).build();
			branch.activateVehicle(id);
			return Response.status(204).build();
		}catch(Exception e) {
			return Response.status(500).build();
		}
	}
	
	@POST
	@Path("/{vehicleId}/addPassenger")
	public Response addPassenger(@PathParam("vehicleId") String vehicleId) {
		try {
			Vehicle v = branch.getVehicle(vehicleId);
			if(v == null) return Response.status(404).build();
			branch.addPassenger(vehicleId);
			return Response.status(204).build();
		}catch(Exception e) {
			return Response.status(500).build();
		}
	}
	
	@DELETE
	@Path("/{vehicleId}/remove")
	public Response removeVehicle(@PathParam("vehicleId") String vehicleId) {
		try {
			Vehicle v = branch.getVehicle(vehicleId);
			if(v == null) return Response.status(404).build();
			branch.removeVehicle(vehicleId);
			return Response.status(200).build();
		}catch(Exception e) {
			return Response.status(500).build();
		}
	}
	
}
