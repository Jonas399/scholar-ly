package com.scholar.projektseminar_programmierung;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.PatternSyntaxException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.scraperapi.ScraperApiClient;

import kong.unirest.Unirest;

public class CompactSearch {
	private String term;
	private int year_begin;
	private int year_end;
	private int[] hitsPerYear;
	private String timeStamp;
	private ScraperApiClient client;
	private String apiKey;
	private static LinkBuilder linkBuilder;
	
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public int getYear_begin() {
		return year_begin;
	}
	public void setYear_begin(int year_begin) {
		this.year_begin = year_begin;
	}
	public int getYear_end() {
		return year_end;
	}
	public void setYear_end(int year_end) {
		this.year_end = year_end;
	}
	public int[] getHitsPerYear() {
		return hitsPerYear;
	}
	public void setHitsPerYear(int[] hitsPerYear) {
		this.hitsPerYear = hitsPerYear;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public ScraperApiClient getClient() {
		return client;
	}
	public void setClient(ScraperApiClient client) {
		this.client = client;
	}
	public String getApiKey() {
		return apiKey;
	}
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	
	//Search is executed when initializing object of type CompactSearch
	public CompactSearch(String term, int year_begin, int year_end, String key, LinkBuilder linkBuilder) {
		super();
		
		//Init Metadata
		this.term = term;
		this.year_begin = year_begin;
		this.year_end = year_end;
		this.timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		
		System.out.println(timeStamp +": New Search"); //Info for user
		
		System.out.println("Term: "+this.getTerm()+"; YearBegin: "+this.getYear_begin()+"; YearEnd: "+this.getYear_end()+";");
		
		this.client = this.initClient(key); //initialize scaper api client

		this.performSubSearches(linkBuilder); //Perform search for each year. Scraping and filtering out number from html element is done here. Result is saved to array hitsPerYear
		
		this.buildCSV(); //At the end csv file gets created
	}
	
	public ScraperApiClient initClient(String key) {
		System.out.println("Setting up ScraperApi client...");
		Unirest.config().reset();
		ScraperApiClient tempClient = new ScraperApiClient(key);
		return tempClient;
	}
	
	
	public String scrapeScholarHits(String term, int year_begin, int year_end, LinkBuilder lb) {
		
		String url = lb.buildLink(term,year_begin,year_end); //Build the Link for term and search year
		
		try  {
			//Prepare HTTP Client for next Scrape
			Unirest.config().reset();
			Unirest.config().verifySsl(false); //To prevent unirest from rejecting self-signed certificate (otherwise faulty scrapes)
			
			String html = this.client.get(url).result(); //Save scrape result in string

			//Print Scrape to File to see what was accessed
			PrintWriter out = new PrintWriter("scrape.html");
			out.println(html);
			out.close();
			
			Document doc = Jsoup.parse(html); //Parse to Document to find each html element fast
			
			String scholarHits = doc.select("div.gs_ab_mdw").text();
			
			return scholarHits; //Return String containing the html element which displays information about found results 
		
		} catch(Exception e) {
			return e.toString(); 
		}
	}
	
	public int extractHits(String scholarHits) {	//Get search results number from html element (in the form of string)
		int result = -1;
		
		try {
		
			String[] splitArray = scholarHits.split("\\s+"); //each word of string (separated by whitespace) as element of array
			String temp = "0"; //Default init string which contains result
			
			if(splitArray.length >= 4) { //Array should not contain more than 4 elements (words).
					
				temp = splitArray[3].replace(".",""); //Normally result is the 4th word in the array
				
				try {
						result = Integer.parseInt(temp); 
						
					} catch (NumberFormatException e) {	//In case of no "Ungefähr"/"Apporximately" in div. Occurs when search yield very few results
						temp = splitArray[2].replace(",", ""); //In this case, result is the 3rd word in the array
						result = Integer.parseInt(temp);
					}		
			} 
			return result; //Return the result as int
		} catch (PatternSyntaxException ex) {
			return -1; //If the html element changes, result will be -1
		}
	}
	
	public void performSubSearches(LinkBuilder lb) {
		int difference = this.year_end - this.year_begin + 1; //Init array holding hits per year, size is all the years to be searched
		int[] tempInt = new int[difference];
		
		this.hitsPerYear = tempInt;
		
		//Start searching for each year
		for(int i = this.year_end; i>=this.year_begin; i--) {
			
			int arrIdx = this.year_end-i;
			
			int hits = -1; //helper number
			int cnt = 0; //count for while (only execute 3 times
			//Preventing obviously faulty scrapes (tests have shown ~5% probability of hits being 0 for one year). If scraped result is 0 three times in a row,
				//0 will be saved
			while(hits <= 0 && cnt!=3) {
				hits = this.extractHits(scrapeScholarHits(this.term,i,i,lb)); //Scrape hits
				++cnt;
			}
			
			this.hitsPerYear[arrIdx] = hits; //save hits for year to array
			
			System.out.println("Hits for "+i+": "+this.hitsPerYear[arrIdx]); //Print out hits for a year
		}
	}
	
	public void buildCSV() {
		CSVBuilder csv = new CSVBuilder(this);
		csv.print();
	}
	
	public static void main(String[] args) {
		//For testing purposes
		LinkBuilder lb = new LinkBuilder();
		CompactSearch s1 = new CompactSearch("golang",2007,2007,"69de02736440f3f2252629653b808be1", lb);		
	}
	
}