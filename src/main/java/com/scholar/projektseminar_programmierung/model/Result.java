package com.scholar.projektseminar_programmierung.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.scholar.projektseminar_programmierung.Scraper;
import com.scholar.projektseminar_programmierung.Search;



@Entity
@Table(name = "result")
public class Result {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(name="search_term")
	private String term;
	
	@Column(name="hits")
	private int hits;
	
	@Column(name="year_begin")
	private int year_begin;
	
	@Column(name="year_end")
	private int year_end;
	
	@Column(name="metadata")
	private String metadata;
	 
	public Result(String term) {
		super();
		this.term=term;
		Search temp = new Search(term);
		this.hits=temp.getHits();
	}
	
	
	public Result(String term, int year_begin, int year_end, String metadata) {
		super();
		this.term = term;
		Search temp = new Search(term,year_begin,year_end);
		this.hits=temp.getHits();
		this.year_begin = year_begin;
		this.year_end = year_end;
		this.metadata = metadata;
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
	public int getHits() {
		return hits;
	}
	public void setHits(int hits) {
		this.hits = hits;
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
	public String getMetadata() {
		return metadata;
	}
	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}
	
	
}
