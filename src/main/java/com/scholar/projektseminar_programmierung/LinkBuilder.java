package com.scholar.projektseminar_programmierung;


public class LinkBuilder {
	public String buildLink(String searchTerm) {
		//beginString and endString are consistent for standard search
		//TODO: Add possibility to add time aspect to search --> other string format
		String beginString =  "https://scholar.google.de/scholar?hl=de&as_sdt=0%2C5&q=";
	    String endString =	"&btnG=";
	    
	    //replace "" with "+" to ensure right format for link --> multiple terms can be used
	    searchTerm = searchTerm.replace(" ", "+");
	    
	    String url = beginString + searchTerm + endString;
	    
	    return url;
	}
}
