package com.scholar.projektseminar_programmierung;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import org.mockito.Mock;

import com.scraperapi.ScraperApiClient;

//INFO: It is crucial to keep order of tests correctly --> set/ get methods may fail otherwise
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
	static CompactSearch cs;
	@BeforeAll
	static void generateCompactSearch(){
		cs = new CompactSearch("natural AND disaster", 2015, 2016, "240327b0521b2438b20eeefe95e62f4e");
	}
	
	@Test
	@Order(1)
	void testIfSearchTermGetsReturnedCorrectly() {
	
		assertEquals("natural AND disaster", cs.getTerm());
	}
	
	@Test
	@Order(2)
	void testIfSearchTermGetsSetCorrectly() {
		cs.setTerm("new Search Term");
		
		assertEquals("new Search Term", cs.getTerm());
	}
	
	@Test
	@Order(3)
	void testIfYearBeginGetsReturnedCorrectly() {

		assertEquals(2015, cs.getYear_begin());
	}
	
	@Test
	@Order(4)
	void testIfYearEndGetsReturnedCorrectly() {

		assertEquals(2016, cs.getYear_end());
	}
	
	@Test
	@Order(5)
	void testIfSetYearBeginFunctionsProperly() {
		
		cs.setYear_begin(2014);
		assertEquals(2014, cs.getYear_begin());
		}
	
	@Test
	@Order(6)
	void testIfSetYearEndFunctionsProperly() {
		
		cs.setYear_end(2019);
		assertEquals(2019, cs.getYear_end());
		}
	
	@Test
	@Order(7)
	void testIfExtractedHitsAreCorrect() {
		
		assertEquals(106000, cs.extractHits("Artikel Scholar Ungefähr 106.000 Ergebnisse (0,03 Sek.)"));
	}
	
	@Test
	@Order(8)
	void testIfExtractedHitsAreCorrect_IfNoResultsAreFound() {
		
		assertEquals(0, cs.extractHits("Keine ergebnisse"));
	}
	
	@Test
	@Order(9)
	void testIfLenghtOfSubSearchArrayIsCorrect() {
		
		assertEquals(2, cs.getHitsPerYear().length);
	}
	
	@Test
	@Order(10)
	void testIfHitsPerYearGetsSetCorrectly() {
		int[] newArray = {2010, 2011, 2012};
		cs.setHitsPerYear(newArray);
		
		assertEquals(newArray ,cs.getHitsPerYear());
	}
	
	@Test
	@Order(11)
	void testIfTimeStampGetsReturnedCorrectly() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd");  
		LocalDateTime currentDate = LocalDateTime.now();  
		
		assertEquals(dtf.format(currentDate), cs.getTimeStamp());
	}
	
	@Test
	@Order(12)
	void testIfTimeStampGetsSetCorrectly() {
		cs.setTimeStamp("2020.01.10");
		
		assertEquals("2020.01.10", cs.getTimeStamp());
	}
	
	@Test
	@Order(13)
	void testIfSetApiKeyWorksProperly() {
		cs.setApiKey("abc");
		assertEquals("abc", cs.getApiKey());
	}
	
	/*INFO: method scrapeScholarHits not testable, due to inconsistency of google scholar data scrape
	@Test
	void testIfContentsOfSubSearchArrayAreCorrect() {
		CompactSearch cs = new CompactSearch("natural AND disaster", 2015, 2015, "240327b0521b2438b20eeefe95e62f4e");
			
	}*/

}



