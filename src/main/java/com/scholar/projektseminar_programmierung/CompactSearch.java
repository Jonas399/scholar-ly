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
	
	
	public CompactSearch(String term, int year_begin, int year_end, String key) {
		super();
		this.term = term;
		this.year_begin = year_begin;
		this.year_end = year_end;
		this.timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		System.out.println(timeStamp +": New Search");
		System.out.println("Term: "+this.getTerm()+"; YearBegin: "+this.getYear_begin()+"; YearEnd: "+this.getYear_end()+";");
		this.client = this.initClient(key);
		//this.apiKey = apiKey;
		this.performSubSearches();
		this.buildCSV();
	}
	
	public ScraperApiClient initClient(String key) {
		System.out.println("Setting up ScraperApi client...");
		Unirest.config().reset();
		ScraperApiClient tempClient = new ScraperApiClient(key);
		return tempClient;
	}
	
	static LinkBuilder linkBuilder = new LinkBuilder();
	
	public String scrapeScholarHits(String term, int year_begin, int year_end, LinkBuilder lb) {
		
		String url = lb.buildLink(term,year_begin,year_end);
		
		Random waitTimer = new Random();
		long wait = waitTimer.nextInt(10)+5;
		
		try  {
			//System.out.println("Delaying search by " + wait + " seconds to avoid bot detection.");
			//Thread.sleep(wait*1000);
			Unirest.config().reset();
			String html = this.client.get(url+"&country_code=us").result();
			
			//Debugging: Print String to file so we can see what was scraped:
			PrintWriter out = new PrintWriter("scrape.html");
			out.println(html);
			out.close();
			
			Document doc = Jsoup.parse(html);
			String scholarHits = doc.select("div.gs_ab_mdw").text();
			return scholarHits;
		} catch(Exception e) {
			return e.toString();
		}
	}
	
	public int extractHits(String scholarHits) {
		int result = -1;
		try {
			String[] splitArray = scholarHits.split("\\s+");
			String temp = "0";
			if(splitArray.length >= 4) {
			temp = splitArray[3].replace(".","");
			}
			result = Integer.parseInt(temp);
			return result;
		} catch (PatternSyntaxException ex) {
			return -1;
		}
	}
	
	public void performSubSearches() {
		//Init array holding hits per year
		int difference = this.year_end - this.year_begin + 1;
		int[] tempInt = new int[difference];
		this.hitsPerYear = tempInt;
		for(int i = this.year_end; i>=this.year_begin; i--) {
			int arrIdx = this.year_end-i;
			this.hitsPerYear[arrIdx] = this.extractHits(scrapeScholarHits(this.term,i,i,linkBuilder));
			System.out.println("Hits for "+i+": "+this.hitsPerYear[arrIdx]);
		}
	}
	
	public void buildCSV() {
		CSVBuilder csv = new CSVBuilder(this);
		csv.print();
	}
	
	public static void main(String[] args) {
		CompactSearch s1 = new CompactSearch("machine learning",2000,2020,"240327b0521b2438b20eeefe95e62f4e");		
	}
	
}