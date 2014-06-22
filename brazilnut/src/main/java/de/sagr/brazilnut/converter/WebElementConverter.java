package de.sagr.brazilnut.converter;

import org.openqa.selenium.WebElement;

import com.codeborne.selenide.SelenideElement;

import de.sagr.brazilnut.exception.UnsupportedConversionException;

public class WebElementConverter implements IConverter<WebElement> {

	@Override
	public WebElement convertToModel(SelenideElement value) {
		return value.toWebElement();
	}

	@Override
	public void writeToWebElement(WebElement model, SelenideElement element) {
		throw new UnsupportedConversionException("Writing WebElement to page is not supported.");
	}

}
