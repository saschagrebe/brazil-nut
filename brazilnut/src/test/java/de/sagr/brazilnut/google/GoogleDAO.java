package de.sagr.brazilnut.google;

import com.codeborne.selenide.SelenideElement;

import de.sagr.brazilnut.annotation.WebItem;

public class GoogleDAO {

	@WebItem(name = "q")
	private String searchField;
	
	@WebItem(name = "btnG")
	private SelenideElement searchButton;
	
	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}
	
	public void search() {
		searchButton.click();
	}
}
