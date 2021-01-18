package com.scholar.projektseminar_programmierung.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.scholar.projektseminar_programmierung.CompactSearch;
import com.scholar.projektseminar_programmierung.LinkBuilder;



@Entity
@Table(name = "result")
public class Result {
	
	
	//JPA Creates Table result" with the following columns
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(name="term")
	private String term;
	
	
	@Column(name="hitsPerYear")
	private int[] hitsPerYear;
	
	@Column(name="year_begin")
	private int year_begin;
	
	@Column(name="year_end")
	private int year_end;
	
	@Column(name="timestamp")
	private String timestamp;
	
	//Default Constructor required for accessing the data (GET Requests)
	public Result() {
		super();
		System.out.println("Called default constructor"); //Info for devs
		
	}
	
	
	//Constructor for saving a new result. Search is started when this is called.
	//Data is fed in JSON format
	@JsonCreator 
	public Result(@JsonProperty("term") String term, @JsonProperty("year_begin") int year_begin, @JsonProperty("year_end") int year_end, @JsonProperty("key") String key) {
	    System.out.println("n-args"); //Info for dev
		
	    //Search is started
	    LinkBuilder linkBuilder = new LinkBuilder();
	    CompactSearch temp = new CompactSearch(term,year_begin,year_end, key, linkBuilder);
	    
	    //Data from search is copied into result
	    this.timestamp = temp.getTimeStamp();
	    this.term = temp.getTerm();
	    this.year_begin = temp.getYear_begin();
	    this.year_end = temp.getYear_end();
	    this.hitsPerYear = temp.getHitsPerYear();
	}
	

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}

	
	public int[] getHitsPerYear() {
		return hitsPerYear;
	}


	public void setHitsPerYear(int[] hitsPerYear) {
		this.hitsPerYear = hitsPerYear;
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

	public String getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	
	
}
