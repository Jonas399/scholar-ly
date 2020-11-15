package com.scholar.projektseminar_programmierung;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LinkBuilderTest {

	LinkBuilder linkBuilderTest = new LinkBuilder();
	
	@Test
	void testIfLinkGetsCreatedCorrectly() {
		assertEquals("https://scholar.google.de/scholar?hl=de&as_sdt=0%2C5&q=natural+AND+disaster&btnG=", linkBuilderTest.buildLink("natural AND disaster"));
	}

}
