package osm2sqlite;

public class Way extends Element {
	String toJson() {
		String children = children();
		if(children != null) {
			return "{id: " + id + ", " + children + "}";			
		}
		return "{id: " + id + "}";			
	}
}
