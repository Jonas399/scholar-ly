package webscraper;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class scraper {

	public static void main(String[] args) {
		System.out.println(getAmountOfHits());
	}
	
	public static String getAmountOfHits() {
		LinkBuilder linkBuilder = new LinkBuilder();
		
		String url = linkBuilder.buildLink("natural AND disaster");
		String hits = "";
		
		try{
			final Document document = Jsoup.connect(url).get();
		
			hits = document.select("div.gs_ab_mdw").text();
		
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return hits;
	}
}

