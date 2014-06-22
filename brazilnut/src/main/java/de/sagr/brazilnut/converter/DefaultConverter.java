package de.sagr.brazilnut.converter;

import com.codeborne.selenide.SelenideElement;

public class DefaultConverter implements IConverter<Object> {

	@Override
	public Object convertToModel(SelenideElement value) {
		return value.val();
	}

	@Override
	public void writeToWebElement(Object modelValue, SelenideElement element) {
		element.setValue(String.valueOf(modelValue));
	}

}
