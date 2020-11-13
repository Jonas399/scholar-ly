package webscraper;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class scraper {

	public static void main(String[] args) {
	String beginString =  "https://scholar.google.de/scholar?hl=de&as_sdt=0%2C5&q=";
    String endString =	"&btnG=";
    
    String searchTerms = "drone";
    
    String url = beginString + searchTerms + endString;
	try{
		final Document document = Jsoup.connect(url).get();
		

		
		String test = document.select("div.gs_ab_mdw").text();
		System.out.println(test); 
		
	}catch(Exception e) {
		e.printStackTrace();
	}
	}
}

