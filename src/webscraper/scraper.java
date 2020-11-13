package webscraper;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class scraper {

	public static void main(String[] args) {
	final String url =  "https://de.wikipedia.org/wiki/Wikipedia:Hauptseite";
	try{
		final Document document = Jsoup.connect(url).get();
		

		
		String test = document.selectFirst("p.hauptseite-absatz").text();
		System.out.println(test); 
		
	}catch(Exception e) {
		e.printStackTrace();
	}
	}
}

