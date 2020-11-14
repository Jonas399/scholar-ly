package webscraper;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class scraper {

	public static void main(String[] args) {
		System.out.println(getAmountOfHits("natural AND disaster"));
	}
	
	public static String getAmountOfHits(String searchTerm) {
		LinkBuilder linkBuilder = new LinkBuilder();
		
		String url = linkBuilder.buildLink(searchTerm);
		String hits = "";
		
		try{
			final Document document = Jsoup.connect(url).get();
		
			hits = document.select("div.gs_ab_mdw").text();
			hits = hits.substring(25, hits.length()-23);
		
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return hits;
	}
}

