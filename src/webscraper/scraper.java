package webscraper;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
//import webscraper.LinkBuilder;

public class scraper {

	public static void main(String[] args) {
		
		LinkBuilder linkBuilder = new LinkBuilder();
		
		String url = linkBuilder.buildLink("natural AND disaster");
		
		try{
			final Document document = Jsoup.connect(url).get();
		
			String test = document.select("div.gs_ab_mdw").text();
			System.out.println(test); 
		
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}

