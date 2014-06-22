package de.sagr.brazilnut.converter;

import com.codeborne.selenide.SelenideElement;

@SuppressWarnings("rawtypes")
public class ConverterDescriptor {

	public final Class attributeClass;
	
	public final String webElementName;
	
	public ConverterDescriptor(Class attributeClass, SelenideElement webElement) {
		this.attributeClass = attributeClass;
		if (webElement != null) {
			this.webElementName = webElement.getTagName();
		} else {
			this.webElementName = null;
		}
	}
	
	public ConverterDescriptor(Class attributeClass) {
		this(attributeClass, null);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((attributeClass == null) ? 0 : attributeClass.hashCode());
		result = prime * result
				+ ((webElementName == null) ? 0 : webElementName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConverterDescriptor other = (ConverterDescriptor) obj;
		if (attributeClass == null) {
			if (other.attributeClass != null)
				return false;
		} else if (!attributeClass.equals(other.attributeClass))
			return false;
		if (webElementName == null) {
			if (other.webElementName != null)
				return false;
		} else if (!webElementName.equals(other.webElementName))
			return false;
		return true;
	}
	
}
