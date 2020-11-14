package webscraper;

import java.util.regex.PatternSyntaxException;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
//import webscraper.LinkBuilder;

public class scraper {
	
	public static int results(String text_results) {
		int result = -1;
		
		try {
			String[] splitArray = text_results.split("\\s+");
			String temp = splitArray[3].replace(".","");
			result = Integer.parseInt(temp);
			return result;
		} catch (PatternSyntaxException ex) {
			return -1;
		}
		
		
		
		
	}

	public static void main(String[] args) {
		
		LinkBuilder linkBuilder = new LinkBuilder();
		
		String url = linkBuilder.buildLink("tt");
		
		try{
			final Document document = Jsoup.connect(url).get();
		
			String test = document.select("div.gs_ab_mdw").text();
			System.out.println(test); 
			System.out.println(results(test));
		
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}

