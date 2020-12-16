package com.scholar.projektseminar_programmierung;


public class LinkBuilder {
	public String buildLink(String searchTerm,int year_begin, int year_end) {
		//beginString and endString are consistent for standard search
		//TODO: Add possibility to add time aspect to search --> other string format
		String beginString =  "http://scholar.google.de/scholar?hl=de&as_sdt=0%2C5&q=";
	    String midString =	"&hl=de&as_sdt=0%2C5&as_";
	    String yearLow = "ylo=";
	    String betweenYear = "&as_";
	    String yearHigh = "yhi=";
	    
	    if(year_begin > 0 ) {
	    	yearLow += Integer.toString(year_begin);
	    }
	    
	    if(year_end > 0 && year_end>=year_begin) {
	    	yearHigh += Integer.toString(year_end);
	    }
	    
	    //replace "" with "+" to ensure right format for link --> multiple terms can be used
	    searchTerm = searchTerm.replace(" ", "+");
	    
	    String url = beginString + searchTerm + midString + yearLow + betweenYear + yearHigh;
	    
	    return url;
	}
	
	public static void main(String args[]) {
		LinkBuilder b1 = new LinkBuilder();
		System.out.println(b1.buildLink("kernel", 2019, 2019));
		
	}
}
