package com.scholar.projektseminar_programmierung;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.scraperapi.ScraperApiClient;

class CompactSearchTest {
	
	//INFO: method scrapeScholarHits not testable, due to inconsistency of google scholar data scrape
	//@Test
	//void testIfNumberOfHitsGetsExtractedCorrectly() {
	//	CompactSearch cs = new CompactSearch("natural AND disaster", 2015, 2015, "240327b0521b2438b20eeefe95e62f4e");
	//	
	//	LinkBuilder mockLinkBuilder = mock(LinkBuilder.class);
	//	
	//	when(mockLinkBuilder.buildLink("natural AND disaster", 2015, 2015)).thenReturn("http://scholar.google.de/scholar?hl=de&as_sdt=0%2C5&q=natural+AND+disaster&hl=de&as_sdt=0%2C5&as_ylo=2015&as_yhi=2015");
	//	
	//	assertEquals("Artikel Scholar Ungefähr 106.000 Ergebnisse (0,03 Sek.)", cs.scrapeScholarHits("natural AND disaster", 2015, 2015, mockLinkBuilder));
	//}

	@Test
	void testIfExtractedHitsAreCorrect() {
		CompactSearch cs = new CompactSearch("natural AND disaster", 2015, 2015, "240327b0521b2438b20eeefe95e62f4e");
		
		assertEquals(106000, cs.extractHits("Artikel Scholar Ungefähr 106.000 Ergebnisse (0,03 Sek.)"));
	}
	
	@Test
	void testIfExtractedHitsAreCorrect_IfNoResultsAreFound() {
		CompactSearch cs = new CompactSearch("asdfjkdhsgkjahsdgla", 2015, 2015, "240327b0521b2438b20eeefe95e62f4e");
		
		assertEquals(0, cs.extractHits("Keine ergebnisse"));
	}
	
	@Test
	void testIfLenghtOfSubSearchArrayIsCorrect() {
		CompactSearch cs = new CompactSearch("natural AND disaster", 2015, 2016, "240327b0521b2438b20eeefe95e62f4e");
		
		assertEquals(2, cs.getHitsPerYear().length);
	}
	
	/*INFO: method scrapeScholarHits not testable, due to inconsistency of google scholar data scrape
	@Test
	void testIfContentsOfSubSearchArrayAreCorrect() {
		CompactSearch cs = new CompactSearch("natural AND disaster", 2015, 2015, "240327b0521b2438b20eeefe95e62f4e");
			
	}*/
}



