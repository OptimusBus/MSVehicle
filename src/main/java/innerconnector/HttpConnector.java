package innerconnector;

import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.core.Response;
import org.apache.cxf.jaxrs.client.WebClient;

public class HttpConnector {
	
	public HttpConnector() {}
	
	/**
	 * Generic request maker for Http request
	 * @param url the url of the request
	 * @param m the type of request to execute (GET, POST, DELETE, PUT)
	 * @param queryParam a map of query param - value for multiple param GET request
	 * @param params the params for POST and PUT request
	 * @return a Response or null
	 */
	private static Response makeRequest(String url, Method m, Map<String,String> queryParam, String params) {	
		WebClient client = WebClient.create(baseAddress);
		client.accept("application/json");
		client.type("application/json");
		client.path(url);
		switch(m) {
			case GET:
				if(queryParam != null) {
					for (Entry<String, String> entry : queryParam.entrySet()) {
				        client.query(entry.getKey(), entry.getValue());
				    }
				}
				return client.get();
			case POST:
				if(params != null) return client.post(params);
			case PUT:
				if(params != null) return client.put(params);
			case DELETE:
				if(params != null) return client.delete();
			default:
				return null;
		}
	}
	
	
	/**
	 * Request vehicle registration from security service
	 * @param params the params for POST and PUT request
	 * @return a Response or null
	 */
	public static Response requestVehicleRegistration(String param) {
		return makeRequest("/security/requestVehicleReg", Method.POST, null, param);
	}
	
	/**
	 * Request best vehicle from route service
	 * @param params the params for POST and PUT request
	 * @return a Response or null
	 */
	public static Response requestBestVehicle(String param) {
		return makeRequest("/routes/bestVehicle", Method.POST, null, param);
	}
	
	/**
	 * Request vehicle route from route service
	 * @param params the params for POST and PUT request
	 * @return a Response or null
	 */
	public static Response requestVehicleRoute(String param) {
		return makeRequest("/routes/"+param, Method.GET, null, null);
	}
	
	public static enum Method {GET, POST, PUT, DELETE}
	private static final String baseAddress = "http://gateway-optimusbus.router.default.svc.cluster.local/optimusbus";
}
