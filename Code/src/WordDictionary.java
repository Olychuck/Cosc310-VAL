import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
	/**
	 * Class used for lookup on words that are synonyms of
	 * other words and various String methods
	 *
	 */
public class WordDictionary {
	/**
	 * return the location from a String by 
	 * finding words with capitals
	 */
	public String locationParse(String a) {
		int nw = 1;
		for (int i = 0; i < a.length(); i++) {
			if (a.charAt(i) == ' ')
				nw++;
		}
		if (nw <= 2)
			return a;
		else {
			String ret = "";
			a = a.substring(1);
			for (int i = 0; i < a.length(); i++) {
				if (a.charAt(i) >= 65 && a.charAt(i) <= 90)
					while (true) {
						ret= ret + a.charAt(i++);
						if ( i == a.length()||a.charAt(i) == ' ' ){
							ret=ret+" ";
							break;
						}
					}
			}
			return ret.substring(0,ret.length()-1);
		}
	}
	/**
	 * Uses a scanner to get input from a user,
	 * Continually asks until user replies with a yes or no
	 */
	public boolean yes(Scanner scan) {
		while (true) {
			String us = scan.nextLine().toLowerCase();
			if (yesSir(us)) {
				return true;
			} else if (noSir(us)) {
				return false;
			} else {
				System.out
						.println("Answer the question...");
			}
		}

	}
	/**
	 * Determines if String contains a yes word
	 */
	public boolean yesSir(String s) {
		for (String a : yes) {
			if (s.contains(a))
				return true;
		}
		return false;
	}
	/**
	 * Determines if String contains a no word
	 */
	public boolean noSir(String s) {
		for (String a : no) {
			if (s.contains(a))
				return true;
		}
		return false;
	}
	/**
	 * Creates a Hashset from a given String
	 */
	public static HashSet<String> creator(String a, String reg) {
		String[] arr = a.split(reg);
		HashSet<String> ret = new HashSet<String>();
		for (String b : arr) {
			ret.add(b);
		}
		return ret;
	}
	/**
	 * Creates an ArrayList from a given String
	 */
	public static ArrayList<String> creatorArr(String a, String reg) {
		String[] arr = a.split(reg);
		ArrayList<String> ret = new ArrayList<String>();
		for (String b : arr) {
			ret.add(b);
		}
		return ret;
	}

	public static ArrayList<String> creatorArr(String a) {
		return creatorArr(a, ", ");
	}

	public static HashSet<String> creator(String a) {
		return creator(a, ", ");
	}
	/**
	 * determines if String contains a mean word
	 */
	public boolean mean(String s){
		for(String t:mean)
			if(s.contains(t))
				return true;
		return false;
	}
	/**
	 * determines if String contains a nice word
	 */
	public boolean nice(String s){
		for(String t:nice){
			if(s.contains(t))
				return true;
		}
		return false;
	}
	/**
	 * determines if String contains a not word
	 */
	public boolean not(String s){
		for(String t:not){
			if(s.contains(t))
				return true;
		}
		return false;
	}
	/**
	 * determines if String contains a bad word
	 */
	public boolean bad(String s){
		for(String t:bad){
			if(s.contains(t))
				return true;
		}
		return false;
	}
	/**
	 * determines if String contains a good word
	 */
	public boolean good(String s){
		for(String t:good){
			if(s.contains(t))
				return true;
		}
		return false;
	}
	ArrayList<String> mean;
	ArrayList<String> nice;
	ArrayList<String> yes;
	ArrayList<String> no;
	ArrayList<String> not;
	ArrayList<String> bad;
	ArrayList<String> good;
	
	public WordDictionary() {
		bad = creatorArr("bad,abominable, amiss, atrocious, awful, bad news, beastly, blah, bottom out, bummer, careless, cheap, cheesy, crappy, cruddy, crummy, defective, deficient, diddly, dissatisfactory, downer, dreadful, erroneous, fallacious, faulty, garbage, godawful, grody, gross, grungy, icky, imperfect, inadequate, incorrect, inferior, junky, lousy, not good, off, poor, raunchy, rough, sad, slipshod, stinking, substandard, synthetic, the pits, unacceptable, unsatisfactory");
		good = creatorArr("good, acceptable, ace, admirable, agreeable, boss, bully, capital, choice, commendable, congenial, crack, deluxe, excellent, exceptional, favorable, first-class, first-rate, gnarly, gratifying, great, honorable, marvelous, neat, nice, pleasing, positive, precious, prime, rad, recherché, reputable, satisfactory, satisfying, select, shipshape, sound, spanking, splendid, sterling, stupendous, super, super-eminent, super-excellent, superb, superior, tip-top, up to snuff, valuable, welcome, wonderful, worthy");
		no = creatorArr("no, absolutely not, by no means, negative, never, nix, no way, not at all, not by any means");
		yes = creatorArr("yes, affirmative, all right, amen, aye, beyond a doubt, by all means, certainly, definitely, even so, exactly, fine, gladly, good, good enough, granted, indubitably, just so, most assuredly, naturally, of course, okay, positively, precisely, sure thing, surely, true, undoubtedly, unquestionably, very well, willingly, without fail, yea, yep, sure");
		nice= creatorArr("nice, admirable, amiable, approved, attractive, becoming, charming, commendable, considerate, copacetic, cordial, courteous, decorous, delightful, ducky, fair, favorable, fine and dandy, friendly, genial, gentle, good, gracious, helpful, ingratiating, inviting, kind, kindly, lovely, nifty*, obliging, okay*, peachy*, pleasant, pleasurable, polite, prepossessing, seemly, simpatico, superior, swell, unpresumptuous, welcome, well-mannered, winning, winsome");
		mean= creatorArr("mean, bad, annoying, close, greedy, mercenary, mingy, miserly, niggard, parsimonious, penny-pinching, penurious, rapacious, scrimpy, selfish, stingy, tight, tight-fisted ");
		not = creatorArr("not, n't");
	}
}
