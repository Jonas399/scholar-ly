package com.scholar.projektseminar_programmierung;

import java.util.Random;
import java.util.regex.PatternSyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Search {
	private String term;
	private int year_begin;
	private int year_end;
	private int hits;
	private int[] hitsPerYear;
	private boolean isSubSearch;
	private String timeStamp;
	
	
	public Search(String term) {
		super();
		this.term=term;
		this.searchInfo();
		this.hits=this.extractHits(this.getSearchMeta(this.term,this.year_begin,this.year_end));
		this.performSubSearching();
		this.timeStamp = this.initTime("yyyy.MM.dd");
		this.buildCSV();
	}
	
	public Search(String term, int year_begin, int year_end) {
		super();
		this.term = term;
		this.year_begin = year_begin;
		this.year_end = year_end;
		this.searchInfo();
		this.hits = this.extractHits(this.getSearchMeta(this.term,this.year_begin,this.year_end));
		this.performSubSearching();
		this.timeStamp = this.initTime("yyyy.MM.dd");
		this.buildCSV();
	}
	
	public Search(String term, int year_begin, int year_end, boolean isSubSearch) {
		super();
		this.term = term;
		this.year_begin = year_begin;
		this.year_end = year_end;
		this.isSubSearch = isSubSearch;
		this.searchInfo();
		this.hits = this.extractHits(this.getSearchMeta(this.term,this.year_begin,this.year_end));
		this.performSubSearching();
		this.timeStamp = this.initTime("yyyy.MM.dd");
		this.buildCSV();
	}
	
	public String initTime(String format) {
		String timeStamp = new SimpleDateFormat(format).format(new Date()); 
		return timeStamp;
	}
	
	public int[] initArray(int year_begin, int year_end) {
		int difference = year_end - year_begin + 1;
		int[] tempInt = new int[difference];
		return tempInt;
	}
	
	public void searchInfo() {
		if(!this.isSubSearch()) {
			System.out.println("Starting search...");
			System.out.println("Term: "+this.getTerm()+"; YearBegin: "+this.getYear_begin()+"; YearEnd: "+this.getYear_end()+";");
		}
	}
	
	public void buildCSV() {
		if(!this.isSubSearch()) {
			CSVBuilder csv = new CSVBuilder(this);
			csv.print();
		}	
	}
	
	public void performSubSearching() {
		if(!this.isSubSearch()) {
			System.out.println("Starting Subsearch...");
			this.hitsPerYear = this.initArray(this.year_begin,this.year_end);
			for(int i = this.year_end; i>=this.year_begin; i-- ) {
				this.hitsPerYear[this.year_end-i] = this.getSubSearchHits(this.term, i);
				System.out.println("Hits for "+i+": "+this.hitsPerYear[this.year_end-i]);
			}
		}
	}
	
	public int getSubSearchHits(String term, int year) {
		Search tempSub = new Search(term, year, year, true);
		return tempSub.getHits();
	}
	
	public int extractHits(String scholar_div) {
		int result = -1;
		try {
			String[] splitArray = scholar_div.split("\\s+");
			String temp = splitArray[3].replace(".","");
			result = Integer.parseInt(temp);
			return result;
		} catch (PatternSyntaxException ex) {
			return -1;
		}
	}
	
	public String getSearchMeta(String term, int year_begin, int year_end) {
		LinkBuilder linkBuilder = new LinkBuilder();
		String url = linkBuilder.buildLink(term,year_begin,year_end);
		
		//To circumvent scraper detection we need a random sleep timer (5-35 seconds)
		Random waitTimer = new Random();
		int wait = waitTimer.nextInt(30)+5;
		System.out.println("Wait " + wait + " seconds before next scrape.");
		
		try{
			Thread.sleep(wait*1000);
			final Document document = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36").get();
		
			String scholarResult = document.select("div.gs_ab_mdw").text();
			return scholarResult;
		} catch(Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public int getHits() {
		return this.hits;
	}
  

	public int[] getHitsPerYear() {
		return hitsPerYear;
	}

	public void setHitsPerYear(int[] hitsPerYear) {
		this.hitsPerYear = hitsPerYear;
	}
	
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

	public boolean isSubSearch() {
		return isSubSearch;
	}

	public void setSubSearch(boolean isSubSearch) {
		this.isSubSearch = isSubSearch;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}
	
	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimestamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	public static void main(String[] args) {
		Search s1 = new Search("machine learning",2000,2020);

		
	}
}
