package osm2sqlite;

import java.util.ArrayList;

public abstract class Element {
	String id;
	ArrayList<Tag> tags = new ArrayList<>();
	ArrayList<Nd> nds = new ArrayList<>();
	ArrayList<Member> members = new ArrayList<>();

	String children() {
		StringBuilder b = new StringBuilder();
		if (tags.size() > 0) {
			b.append("tags: [");
			for (Tag tag : tags) {
				b.append(tag.toJson());
				b.append(", ");
			}
			b.delete(b.length() - 2, b.length() - 1);
			b.append("]");
		}
		if (nds.size() > 0) {
			if (tags.size() > 0) {
				b.append(", ");
			}
			b.append("nds: [");
			for (Nd nd : nds) {
				b.append(nd.toJson());
				b.append(", ");
			}
			b.delete(b.length() - 2, b.length() - 1);
			b.append("]");
		}
		if (members.size() > 0) {
			if (tags.size() > 0 || nds.size() > 0) {
				b.append(", ");
			}
			b.append("members: [");
			for (Member member : members) {
				b.append(member.toJson());
				b.append(", ");
			}
			b.delete(b.length() - 2, b.length() - 1);
			b.append("]");
		}
		return b.isEmpty() ? null : b.toString();

	}

	abstract String toJson();
}
