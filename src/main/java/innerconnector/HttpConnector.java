package innerconnector;
import java.io.BufferedReader;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class HttpConnector {
	
	public HttpConnector() {}
	
	public String makeRequest(String urls, Method m) throws Exception {
		URL url = new URL(urls);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod(m.name());
		con.setConnectTimeout(5000);
		con.setReadTimeout(5000);
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer content = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			content.append(inputLine);
		}	
		in.close();
		return content.toString();
	}
	
	public String test() throws Exception {
		return makeRequest("http://optimusbus-challenge1.router.default.svc.cluster.local/optimusbus/vehicles/VE0001", Method.GET);
	}
	
	public static enum Method {GET, POST, PUT, DELETE, HEAD}
}
