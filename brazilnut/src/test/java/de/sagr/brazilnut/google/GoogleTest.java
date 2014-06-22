package de.sagr.brazilnut.google;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;
import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

public class GoogleTest {

	@Test
	public void basicTest() {
		open("http://www.google.de");
		
		GoogleDAO dao = new GoogleDAO();
		dao.setSearchField("test");
		dao.search();
		
		assertEquals(dao.getSearchField(), "test");
		sleep(10000);
	}
	
}
