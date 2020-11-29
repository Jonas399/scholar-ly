package com.scholar.projektseminar_programmierung.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.scholar.projektseminar_programmierung.CompactSearch;



@Entity
@Table(name = "result")
public class Result {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(name="search_term")
	private String term;
	
	@Column(name="key")
	private String key;
	
	@Column(name="hitsPerYear")
	private int[] hitsPerYear;
	
	@Column(name="year_begin")
	private int year_begin;
	
	@Column(name="year_end")
	private int year_end;
	
	public Result() {
		super();
	}
	 
	public Result(String term, String key) {
		super();
		this.term=term;
		this.key=key;
		CompactSearch tempSearch = new CompactSearch(term,0,2020,key);
		this.hitsPerYear = tempSearch.getHitsPerYear();
		this.year_begin = tempSearch.getYear_begin();
		this.year_end = tempSearch.getYear_end();
	}
	
	
	public Result(String term, int year_begin, int year_end, String key) {
		super();
		this.term = term;
		this.key=key;
		CompactSearch tempSearch = new CompactSearch(term,year_begin,year_end,key);
		this.hitsPerYear = tempSearch.getHitsPerYear();
		this.year_begin = tempSearch.getYear_begin();
		this.year_end = tempSearch.getYear_end();
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

	
	
}
