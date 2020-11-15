package com.scholar.projektseminar_programmierung;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ScraperTest {
	Scraper scraperTest = new Scraper();
	@Test
	void test() {
		assertEquals("2.900.000", Scraper.getAmountOfHits("natural AND disaster"));
	}

}
