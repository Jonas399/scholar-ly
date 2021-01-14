package com.scholar.projektseminar_programmierung;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LinkBuilderTest {

	LinkBuilder linkBuilderTest = new LinkBuilder();
	
	//Tests check weather the link gets builded correctly
	@Test
	void testIfLinkGetsCreatedCorrectly_noTimeFrame() {
		assertEquals("http://scholar.google.de/scholar?hl=de&as_sdt=0%2C5&q=natural+AND+disaster&hl=de&as_sdt=0%2C5&as_ylo=&as_yhi=", linkBuilderTest.buildLink("natural AND disaster",0,0));
	}

	@Test
	void testIfLinkGetsCreatedCorrectly_yearBegin() {
		assertEquals("http://scholar.google.de/scholar?hl=de&as_sdt=0%2C5&q=natural+AND+disaster&hl=de&as_sdt=0%2C5&as_ylo=2010&as_yhi=", linkBuilderTest.buildLink("natural AND disaster", 2010, 0));
	}
	
	@Test
	void testIfLinkGetsCreatedCorrectly_yearEnd() {
		assertEquals("http://scholar.google.de/scholar?hl=de&as_sdt=0%2C5&q=natural+AND+disaster&hl=de&as_sdt=0%2C5&as_ylo=&as_yhi=2010", linkBuilderTest.buildLink("natural AND disaster", 0, 2010));
	}
	
	@Test
	void testIfLinkGetsCreatedCorrectly_yearBeginAndyearEnd() {
		assertEquals("http://scholar.google.de/scholar?hl=de&as_sdt=0%2C5&q=natural+AND+disaster&hl=de&as_sdt=0%2C5&as_ylo=2015&as_yhi=2020", linkBuilderTest.buildLink("natural AND disaster", 2015, 2020));
	}
	
	//Tests check result with negative parameters
	@Test
	void testIfLinkGetsCreatedCorrectly_yearBeginIsNegative() {
		assertEquals("http://scholar.google.de/scholar?hl=de&as_sdt=0%2C5&q=natural+AND+disaster&hl=de&as_sdt=0%2C5&as_ylo=&as_yhi=", linkBuilderTest.buildLink("natural AND disaster", -2010, 0));
	}
	
	@Test
	void testIfLinkGetsCreatedCorrectly_yearEndIsNegative() {
		assertEquals("http://scholar.google.de/scholar?hl=de&as_sdt=0%2C5&q=natural+AND+disaster&hl=de&as_sdt=0%2C5&as_ylo=&as_yhi=", linkBuilderTest.buildLink("natural AND disaster", 0, -2010));
	}
	
	@Test
	void testIfLinkGetsCreatedCorrectly_yearBeginAndYearEndAreNegative() {
		assertEquals("http://scholar.google.de/scholar?hl=de&as_sdt=0%2C5&q=natural+AND+disaster&hl=de&as_sdt=0%2C5&as_ylo=&as_yhi=", linkBuilderTest.buildLink("natural AND disaster", -2015, -2020));
	}
}
