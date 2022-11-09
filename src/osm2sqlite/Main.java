package osm2sqlite;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

	public static void main(String[] args) {
		
		Connection connection = null;

		try {
			connection = DriverManager.getConnection("jdbc:sqlite:hungary.db");
			PreparedStatement nodes = connection.prepareStatement("insert into nodes values(?, ?)");
			PreparedStatement ways = connection.prepareStatement("insert into ways values(?, ?)");
			PreparedStatement relations = connection.prepareStatement("insert into relations values(?, ?)");
			Statement statement = connection.createStatement();
			
			statement.executeUpdate("drop table if exists nodes");
			statement.executeUpdate("drop table if exists ways");
			statement.executeUpdate("drop table if exists relations");
			statement.executeUpdate("create table nodes (id integer, value string)");
			statement.executeUpdate("create table ways (id integer, value string)");
			statement.executeUpdate("create table relations (id integer, value string)");

			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();


			DefaultHandler handler = new DefaultHandler() {
				Element e;
				double counter;

				public void startElement(String uri, String localName, String qName, Attributes attributes)
						throws SAXException {
					switch (qName) {
						case "node":
							Node node = new Node();
							node.id = attributes.getValue(0);
							node.lat = attributes.getValue(1);
							node.lon = attributes.getValue(2);
							e = node;
							break;
						case "way":
							e = new Way();
							e.id = attributes.getValue(0);

							break;
						case "relation":
							e = new Relation();
							e.id = attributes.getValue(0);

							break;
						case "tag":
							Tag tag = new Tag();
							tag.k = attributes.getValue(0);
							tag.v = attributes.getValue(1);
							e.tags.add(tag);
							
							break;
						case "nd":
							Nd nd = new Nd();
							nd.ref = attributes.getValue(0);
							e.nds.add(nd);
							
							break;
						case "member":
							Member member = new Member();
							member.type = attributes.getValue(0);
							member.ref = attributes.getValue(1);
							member.role = attributes.getValue(2);
							e.members.add(member);
							
							break;
					}

				}

				public void endElement(String uri, String localName, String qName) throws SAXException {
					PreparedStatement statement = null;
					switch (qName) {
					case "node":
						statement = nodes;
					case "way":
						statement = ways;
					case "relation":
						statement = relations;
					}
					
					if(statement != null) {
						try {
							statement.setInt(1, Integer.parseInt(e.id));
							statement.setString(2, e.toJson());
							statement.execute();
						} catch (SQLException e) {
							e.printStackTrace();
						}
						counter++;
						if(counter % 10000 == 0) {
							System.out.println(counter);
						}
					}

				}
			};

			System.out.println("Start processing");
			saxParser.parse("C:\\Users\\dev\\Documents\\Game\\data\\hungary.xml", handler);
			System.out.println("Finished");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
