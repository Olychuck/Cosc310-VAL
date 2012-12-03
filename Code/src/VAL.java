import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoURI;
/**
 * The main class that drives the program
 */
public class VAL {
	//Variables for use with the program
	static Scanner scan = new Scanner(System.in);
	static WordDictionary wd = new WordDictionary();

	/**
	 * 
	 * Greets the user and determines who they are.
	 * Proceeds to get their profile or create one for them.
	 */
	public static BasicDBObject greeting(BasicDBObject searchQuery,
			DBCollection collection) {
		DBCursor cursor;
		String temp;
		System.out.println("Hi!! who if I may ask am I speaking with?");
		temp = scan.nextLine();
		if (!temp.contains(" ")) {
			System.out.println("And you're last name?");
			temp = temp + " " + scan.nextLine();
		}
		searchQuery.put("Name", temp.toLowerCase());
		cursor = collection.find(searchQuery);
		if (cursor.hasNext()) {
			System.out.println("Oh its you " + temp.split(" ")[0]
					+ "! Why didn't you say so!");
			DBObject obj = new BasicDBObject();
			obj = cursor.next();
			collection.remove(obj);
			int t = Integer.parseInt((String) obj.get("Visits"));
			if (t > 1)
				System.out.println("Wow can you believe we've already talked "
						+ t + " times since you started traveling");
			obj.put("Visits", Integer.toString(++t));
			collection.insert(obj);
			return (BasicDBObject) cursor.curr();
		} else {
			System.out.println("Well its very nice to meet you "
					+ temp.split(" ")[0] + "!");
			System.out.println("So are you currently traveling?");
			String us;
			if (wd.yes(scan)) {
				System.out.println("NEATO!!");
			} else {
				conclude("mad");
			}
			searchQuery.put("Visits", "1");
			searchQuery.put("First", true);
			collection.insert(searchQuery);
			cursor = collection.find(searchQuery);
			return (BasicDBObject) cursor.next();
		}
	}
	/**
	 * Tells the person about landmarks that are near them and they ask.
	 * 
	 */
	public static void locQuestions(String loc) throws IOException {
		System.out.println("You know, I have travelled to "+loc+" many times and I know quite a lot about it, do you want to know about any of the attractions?");
		while(true){
			String temp=scan.nextLine();
			System.out.println(wikiUse.getWikiInfo(loc,wd.locationParse(temp)));
			System.out.println("Any other questions about landmarks in "+ loc+"?");
			if(wd.yes(scan))
				System.out.println("What else would you like to know about?");
			else
				break;
		}
		conclude("happy");
		
	}
/**
 * Asks Questions about how the travel is in the current 
 * location compared to previous destinations
 * 
 */
	@SuppressWarnings("unchecked")
	public static void travelTalk(BasicDBObject obj, DBCollection collection) throws IOException {
		ArrayList<String> pastLoc;// Array of a persons previous locations
		String loc;// Current location
		// Not first Visit
		if (!obj.getBoolean("First")) {
			pastLoc = (ArrayList<String>) obj.get("pastLocations");
			System.out.println("So did you end up making your way to "
					+ obj.getString(("Next")) + "?");
			loc = obj.getString("Next");
			if (!wd.yes(scan)) {
				System.out.println("Then where are you then?");
				loc = wd.locationParse(scan.nextLine());
				if (pastLoc.contains(loc)) {
					System.out
							.println("Well you've already talked to me about there!");
					locQuestions(loc);
					return;
				}
				System.out.println("Oh " + loc + " is a very nice place!");
			} else {
				System.out.println("Thats awesome!!");
			}
			pastLoc.add(loc);
			obj.put("pastLocations",pastLoc);
			
			// Get input on the people
			System.out.println("So what do you think of the people there?");
			String temp = scan.nextLine();
			if (wd.mean(temp))
				System.out.println("Thats unfortunate!");
			else if (wd.nice(temp))
				System.out.println("Thats nice!");
			System.out.println("Are they nicer or meaner than the people from "
					+ obj.get("People") + "?");
			temp=scan.nextLine();
			if (wd.mean(temp))
				System.out.println("Thats too bad!");
			else if (wd.nice(temp)) {
				System.out.println("Thats cool!");
				obj.put("People", loc);
			}

			// Get input on the food
			System.out.println("What do you think of the food there?");
			temp = scan.nextLine();
			if (wd.bad(temp))
				System.out.println("Thats too bad!");
			else if (wd.good(temp))
				System.out.println("Thats nice!");
			System.out.println("Would you say its better than "
					+ obj.get("Food") + "?");
			temp=scan.nextLine();
			if (wd.mean(temp))
				System.out.println("That sucks!");
			else if (wd.nice(temp)) {
				System.out.println("Thats Awesome!");
				obj.put("Food", loc);
			}

			// Get input on the lodging
			System.out
					.println("So how do you like the lodging in " + loc + "?");
			temp = scan.nextLine();
			if (wd.bad(temp))
				System.out.println("Thats too bad!");
			else if (wd.good(temp))
				System.out.println("Thats nice!");
			System.out.println("Would you say its better than "
					+ obj.get("Lodging") + "?");
			temp=scan.nextLine();
			if (wd.mean(temp))
				System.out.println("That sucks!");
			else if (wd.nice(temp)) {
				System.out.println("Thats Awesome!");
				obj.put("Lodging", loc);
			}

			// Get overall input
			System.out.println("So what is your overall opinion on " + loc
					+ "?");
			temp = scan.nextLine();
			if (wd.bad(temp))
				System.out.println("Thats too bad!");
			else if (wd.good(temp))
				System.out.println("Thats good!");
			System.out.println("Would you say its better than "
					+ obj.get("Overall") + "?");
			temp=scan.nextLine();
			if (wd.mean(temp))
				System.out.println("That sucks!");
			else if (wd.nice(temp)) {
				System.out.println("Thats Awesome!");
				obj.put("Overall", loc);
			}
			System.out.println("So where are you traveling next?");
			while(true){
				temp=scan.nextLine();
				if(wd.locationParse(temp).equals(""))
					System.out.println("Give me an actual location -_-");
				else{
					obj.put("Next", temp);
					break;
				}
			}
			System.out.println("That should be fun");
			collection.save(obj);
			locQuestions(loc);
		} else {
			// First Visit
			obj.put("First", false);
			System.out.println("So where are you currently traveling?");
			pastLoc = new ArrayList<String>();
			pastLoc.add(wd.locationParse(scan.nextLine()));
			loc = pastLoc.get(0);
			obj.append("pastLocations", pastLoc);
			obj.append("Food", loc);
			obj.append("People", loc);
			obj.append("Lodging", loc);
			obj.append("Overall", loc);

			// Get input on the people
			System.out.println("So what do you think of the people there?");
			String temp = scan.nextLine();
			if (wd.mean(temp))
				System.out.println("Thats unfortunate!");
			else if (wd.nice(temp))
				System.out.println("Thats nice!");

			// Get input on the food
			System.out.println("What do you think of the food there?");
			temp = scan.nextLine();
			if (wd.bad(temp))
				System.out.println("Thats too bad!");
			else if (wd.good(temp))
				System.out.println("Thats cool!");

			// Get input on the lodging
			System.out
					.println("So how do you like the lodging in " + loc + "?");
			temp = scan.nextLine();
			if (wd.bad(temp))
				System.out.println("Thats too bad!");
			else if (wd.good(temp))
				System.out.println("Thats nice!");

			// Get overall input
			System.out.println("So what is your overall opinion on " + loc
					+ "?");
			temp = scan.nextLine();
			if (wd.bad(temp))
				System.out.println("Thats too bad!");
			else if (wd.good(temp))
				System.out.println("Thats good!");
			System.out.println("Where are you travelling next?");
			while(true){
				temp=scan.nextLine();
				if(wd.locationParse(temp).equals(""))
					System.out.println("Give me an actual location -_-");
				else{
					obj.put("Next", wd.locationParse(temp));
					break;
				}
			}
			System.out.println("That should be fun");
			collection.save(obj);
			locQuestions(loc);
		}
	}
	/**
	 * Concludes program
	 */
	public static void conclude(String how) {
		if (how.equals("mad")) {
			System.out.println("You annoy me.... Im leaving");
			System.exit(0);
		}
		if(how.equals("happy")){
			System.out.println("OK I'll talk to you later!!");
			System.exit(0);
		}
	}
	/**
	 * Runs program
	 */
	public static void main(String[] args) throws IOException {
		
				DB db = new MongoURI(
				"mongodb://user1:password@ds031877.mongolab.com:31877/heroku_app9496026")
				.connectDB();
		DBCollection collection = db.getCollection("yourCollection");
		BasicDBObject user;
		BasicDBObject searchQuery = new BasicDBObject();
		user = greeting(searchQuery, collection);
		System.out.println("Do you feel like talking about your travels?");
		if(wd.yes(scan))
			travelTalk(user,collection);
		else{
			System.out.println("Do you have any questions about where you are?");
			if(wd.yes(scan)){
				System.out.println("Where are you right now?");
				String temp=null;
				while(true){
					temp=scan.nextLine();
					if(wd.locationParse(temp).equals(""))
						System.out.println("Give me an actual location -_-");
					else{
						break;
					}
				}
				locQuestions(temp);
			}
		}
		

	}
}
