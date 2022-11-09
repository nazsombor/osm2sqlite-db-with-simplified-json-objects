package osm2sqlite;

public class Tag {
	String k, v;
	public Object toJson() {
		return "{k: \"" + k + "\", v: \"" + v + "\"}";
	}

}
