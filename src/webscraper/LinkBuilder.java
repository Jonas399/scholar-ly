package webscraper;


public class LinkBuilder {
	public String buildLink(String searchTerm) {
		String beginString =  "https://scholar.google.de/scholar?hl=de&as_sdt=0%2C5&q=";
	    String endString =	"&btnG=";
	    
	    String url = beginString + searchTerm + endString;
	    
	    return url;
	}
}
