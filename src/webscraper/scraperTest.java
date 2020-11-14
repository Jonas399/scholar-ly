package webscraper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class scraperTest {
	scraper scraperTest = new scraper();
	@Test
	void test() {
		assertEquals("2.900.000", scraper.getAmountOfHits("natural AND disaster"));
	}

}
