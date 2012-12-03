import java.io.IOException;
import java.util.Scanner;

import javax.security.auth.login.FailedLoginException;
/**
 * Class used for communicating with wikipedia
 */
public class wikiUse {
		/**
		 * Returns data on a given landmark given that it 
		 * is contained within the current location
		 */
	public static String getWikiInfo(String location,String search) throws IOException {
		Wiki w = new Wiki();
		System.err.close();
		String[] arr = w.listPages(search, Wiki.NO_PROTECTION, 0);
		if(arr.length==0)
			return "Never heard of it";
		String diz = w.getSectionText(arr[0], 0);
		Scanner scan = new Scanner(diz);
		String temp = null;
		while (scan.hasNext()) {
			temp = scan.nextLine();
			if (temp.contains(location))
				break;
		}
		if(!temp.contains(location))
			return "That isn't even in "+location;
		if (!scan.hasNext())
			return null;
		while (!temp.equals("}}"))
			temp = scan.nextLine();
		temp = scan.nextLine();
		return wikiParse(temp);
	}
	/**
	 * Parses a String from wikiFormat into Ascii Format
	 */
	public static String wikiParse(String s) {
		String ret = "";

		for (int i = 0; i < s.length(); i++) {
			switch (s.charAt(i)) {
			case '\'':
				continue;
			case '(':
				for (int a = i + 1; a < s.length(); a++)
					if (s.charAt(a) == ')') {
						i = a + 1;
						break;
					}
				continue;
			case '[':
				continue;
			case ']':
				continue;
			case '<':
				if (s.substring(i, i + 5).equals("<ref>"))
					for (int a = i + 5; a < s.length(); a++) {
						if (s.charAt(a) == '<') {
							if (s.substring(a, a + 6).equals("</ref>")) {
								i = a + 5;
								break;
							}
						}
					}
				continue;
			case '.':
				return ret+".";
			}
			ret = ret + s.charAt(i);

		}
		return ret;
	}
	public static void main(String[] args) throws IOException, FailedLoginException{
		System.out.println(getWikiInfo("Dubai","The Dubai Mall") );
	}

}
