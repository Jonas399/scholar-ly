package com.scholar.projektseminar_programmierung;

import java.util.regex.PatternSyntaxException;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Scraper {
	
	public static int results(String text_results) {
		int result = -1;
		
		try {
			String[] splitArray = text_results.split("\\s+");
			String temp = splitArray[3].replace(".","");
			result = Integer.parseInt(temp);
			return result;
		} catch (PatternSyntaxException ex) {
			return -1;
		}
		
		
		
		
	}

	public static void main(String[] args) {
		System.out.println(getAmountOfHits("natural AND disaster"));
	}
	
	public static int getAmountOfHits(String searchTerm) {
		LinkBuilder linkBuilder = new LinkBuilder();
		
		String url = linkBuilder.buildLink(searchTerm);
		int hits = 0;
		
		try{
			final Document document = Jsoup.connect(url).get();
		
			String scholarResult = document.select("div.gs_ab_mdw").text();
			hits = results(scholarResult);
		
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return hits;
	}
}

