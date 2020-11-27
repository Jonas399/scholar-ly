package com.scholar.projektseminar_programmierung;

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
	private boolean isSubSearch;
	
	public Search(String term) {
		super();
		this.term=term;
		this.hits=this.extractHits(this.getSearchMeta(this.term));
	}
	
	public Search(String term, int year_begin, int year_end) {
		super();
		this.term = term;
		this.year_begin = year_begin;
		this.year_end = year_end;
		this.hits = this.extractHits(this.getSearchMeta(this.term));
		if(isSubSearch = false) {
			this.createCSV();
		}
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
	
	public String getSearchMeta(String term) {
		LinkBuilder linkBuilder = new LinkBuilder();
		String url = linkBuilder.buildLink(term,this.year_begin,this.year_end);
		try{
			final Document document = Jsoup.connect(url).get();
		
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

	public static void main(String[] args) throws IOException{
		Search s1 = new Search("uav",0,2016);
		System.out.println(s1.hits);
	}
}
