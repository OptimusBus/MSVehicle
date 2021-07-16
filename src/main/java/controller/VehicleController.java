package controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import model.Location;
import model.VehicleReg;

@Consumes("application/json")
@Produces("application/json")
@Path("/vehicles")
public class VehicleController {
	
	public VehicleController() {
		super();
	}
	
	@GET
	public Response bestVehicleForPosition(@QueryParam("fromLat") double fromLat, @QueryParam("fromLong") double fromLong, 
											@QueryParam("toLat") double toLat, @QueryParam("toLong") double toLong) {
		return null;
	}
	
	@GET
	@Path("/support")
	public Response requestVehicleSupport(@QueryParam("vehicleId") String vehicleId) {
		return null;
	}
	
	@GET
	@Path("/failure")
	public Response vehicleFailureReport(@QueryParam("vehicleId") String vehicleId) {
		return null;
	}
	
	@PUT
	@Path("/setSP")
	public Response bestStandingPoint(String vehicleId) {
		return null;
	}
	
	@POST
	@Path("/sendPosition")
	public Response sendPosition(Location location) {
		return null;
	}
	
	@PUT
	@Path("/endroute")
	public Response endOfRouteReport(String vehicleId) {
		return null;
	}
	
	@POST
	@Path("/create")
	public Response createVehicle(VehicleReg vr) {
		return null;
	}
	
	@POST
	@Path("/activate")
	public Response activateVehicle(String id) {
		return null;
	}
	
	@POST
	@Path("/{vehicleId}/{passengerId}")
	public Response addPassenger(@PathParam("vehicleId") String vehicleId, @PathParam("passengerId") String passengerId) {
		return null;
	}
	
}
