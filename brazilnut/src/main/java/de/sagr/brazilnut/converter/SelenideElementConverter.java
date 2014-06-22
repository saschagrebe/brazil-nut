package de.sagr.brazilnut.converter;

import com.codeborne.selenide.SelenideElement;

import de.sagr.brazilnut.exception.UnsupportedConversionException;

public class SelenideElementConverter implements IConverter<SelenideElement> {

	@Override
	public SelenideElement convertToModel(SelenideElement value) {
		return value;
	}

	@Override
	public void writeToWebElement(SelenideElement model, SelenideElement element) {
		throw new UnsupportedConversionException("Writing SelenideElement to page is not supported.");
	}

}
