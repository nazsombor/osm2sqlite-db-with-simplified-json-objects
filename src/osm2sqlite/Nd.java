package osm2sqlite;

public class Nd {
	String ref;
	public Object toJson() {
		return "{ref: " + ref + "}";
	}

}
