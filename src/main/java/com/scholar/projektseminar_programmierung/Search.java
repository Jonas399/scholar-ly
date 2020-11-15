package com.scholar.projektseminar_programmierung;

import java.util.regex.PatternSyntaxException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Search {
	private String term;
	private int year_begin;
	private int year_end;
	private int hits;
	
	public Search(String term, int year_begin, int year_end) {
		super();
		this.term = term;
		this.year_begin = year_begin;
		this.year_end = year_end;
		this.hits = this.extractHits(this.getSearchMeta(this.term));
	}
	
	public void performSearch() {
		this.hits=this.extractHits(this.getSearchMeta(this.term));
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
	
	
	public static void main(String[] args) {
		Search s1 = new Search("uav",0,0);
		System.out.println(s1.hits);
	}
}
