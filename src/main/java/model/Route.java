package model;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

public class Route {
	public Route() {}
	
	public Route(List<Node> route) {
		this.route = route;
	}
	
	public List<Node> getRoute() {
		return route;
	}

	public void setRoute(List<Node> route) {
		this.route = route;
	}
	
	public static Route decodeRoute(Document d) {
		if(d.size() == 0) return null;
		List<Node> n = new ArrayList<Node>();
		for(int i = 0; i < d.size(); i++) {
			Document doc = (Document) d.get("node"+i);
			n.add(Node.decodeNode(doc));
		}
		return new Route(n);
	}
	
	public static Document encodeRoute(Route r) {
		Document d = new Document();
		if(r == null) return d;
		if(r.getRoute() == null) return d;
		if(r.getRoute().size() == 0) return d;
		int i = 0;
		for(Node n : r.getRoute()) {
			d.append("node"+i, Node.encodeNode(n));
			i++;
		}
		return d;
	}

	private List<Node> route = new ArrayList<Node>();
	
}
