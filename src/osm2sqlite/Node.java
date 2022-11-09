package osm2sqlite;

public class Node extends Element{
	String lat, lon;
	
	String toJson() {
		String children = children();
		if(children != null) {
			return "{id: " + id + ", lat: " + lat + ", lon: " + lon + ", " + children + "}";			
		}
		return "{id: " + id + ", lat: " + lat + ", lon: " + lon + "}";
	}

}
