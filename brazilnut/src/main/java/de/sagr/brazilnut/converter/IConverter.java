package de.sagr.brazilnut.converter;

import com.codeborne.selenide.SelenideElement;

public interface IConverter<MODEL> {

	MODEL convertToModel(SelenideElement value);
	
	void writeToWebElement(MODEL model, SelenideElement element);
	
}
