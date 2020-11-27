package com.scholar.projektseminar_programmierung;

import java.util.Random;
import java.util.regex.PatternSyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Search {
	private String term;
	private int year_begin;
	private int year_end;
	private int hits;
	private int[] hitsPerYear;
  private boolean isSubSearch;
	
	public Search(String term) {
		super();
		this.term=term;
		this.hits=this.extractHits(this.getSearchMeta(this.term,this.year_begin,this.year_end));
		this.performSubSearching();
	}
	
	public Search(String term, int year_begin, int year_end) {
		super();
		this.term = term;
		this.year_begin = year_begin;
		this.year_end = year_end;
		if(isSubSearch = false) {
			this.createCSV();
		}
		this.hits = this.extractHits(this.getSearchMeta(this.term,this.year_begin,this.year_end));
		this.performSubSearching();
	}
	
	public Search(String term, int year_begin, int year_end, boolean isSubSearch) {
		super();
		this.term = term;
		this.year_begin = year_begin;
		this.year_end = year_end;
		this.isSubSearch = isSubSearch;
		this.hits = this.extractHits(this.getSearchMeta(this.term,this.year_begin,this.year_end));
		this.performSubSearching();
		
	}
	
	
	public int[] initArray(int year_begin, int year_end) {
		int difference = year_end - year_begin + 1;
		int[] tempInt = new int[difference];
		return tempInt;
	}
	
	public void performSubSearching() {
		if(!this.isSubSearch) {
			System.out.println("Starting Subsearch...");
			this.hitsPerYear = this.initArray(this.year_begin,this.year_end);
			for(int i = this.year_end; i>=this.year_begin; i-- ) {
				this.hitsPerYear[this.year_end-i] = this.getSubSearchHits(this.term, i);
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
		Random waitTimer = new Random();
		int wait = waitTimer.nextInt(5)+1;
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
  
	public void createCSV(){
		try {
			String csvFile = "/Users/svene/Desktop/Programmierprojekt/Projekt/test3.csv";
			FileWriter writer = new FileWriter(csvFile);
		
			List<Search> search = Arrays.asList(new Search(term,year_end, hits));
			
			CSVUtils.writeLine(writer, Arrays.asList("Search term", "Year", "Hits"));
			for (Search s : search) {
				List<String> list = new ArrayList<>();
				list.add(term);
				list.add(Integer.toString(year_end));
				list.add(Integer.toString(hits));
            
				CSVUtils.writeLine(writer, list);
			}
			writer.flush();
			writer.close();
		} catch(Exception e) {
			System.out.println("Error 404");
		}
	}

	public int[] getHitsPerYear() {
		return hitsPerYear;
	}

	public void setHitsPerYear(int[] hitsPerYear) {
		this.hitsPerYear = hitsPerYear;
	}
	
	public static void main(String[] args) {
		Search s1 = new Search("machine learning",2015,2016);
		System.out.println(s1.getHits());
		for(int i : s1.hitsPerYear) {
			System.out.print(i+", ");
		}
	}
}
