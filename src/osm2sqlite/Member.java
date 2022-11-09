package osm2sqlite;

public class Member extends Element {
	String type, ref, role;
	
	String toJson() {
		String children = children();
		if(children != null) {
			return "{id: " + id + ", type: " + type + ", ref: " + ref + ", role: " + role + ", " + children + "}";			
		}
		return "{id: " + id + ", type: " + type + ", ref: " + ref + ", role: " + role + "}";			
	}

}
