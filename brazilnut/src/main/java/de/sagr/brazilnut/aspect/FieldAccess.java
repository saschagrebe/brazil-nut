package de.sagr.brazilnut.aspect;

import static com.codeborne.selenide.Selenide.$;

import java.lang.reflect.Field;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.FieldSignature;
import org.openqa.selenium.By;

import com.codeborne.selenide.SelenideElement;

import de.sagr.brazilnut.annotation.WebItem;
import de.sagr.brazilnut.converter.DefaultConverter;
import de.sagr.brazilnut.converter.IConverter;
import de.sagr.brazilnut.converter.Registry;

@Aspect
public class FieldAccess {

	@Pointcut("set(@de.sagr.brazilnut.annotation.WebItem * *)")
	public void setter() {}
	
	@Pointcut("get(@de.sagr.brazilnut.annotation.WebItem * *)")
	public void getter() {}
	
	@SuppressWarnings("unchecked")
	@Before("setter()")
	public void callSetter(JoinPoint point) {
		final FieldSignature field = (FieldSignature) point.getSignature();
		final WebItem webItem = field.getField().getAnnotation(WebItem.class);
		final SelenideElement webElement = getWebElement(webItem);
		
		final IConverter<Object> converter = (IConverter<Object>) loadConverter(field, webItem, webElement);
		converter.writeToWebElement(point.getArgs()[0], webElement);
	}
	
	@Before("getter()")
	public void callGetter(JoinPoint point) {
		final FieldSignature field = (FieldSignature) point.getSignature();
		final WebItem webItem = getWebItem(field);
		final SelenideElement webElement = getWebElement(webItem);
		
		final Object modelValue = loadConverter(field, webItem, webElement).convertToModel(webElement);
		// set value to original model without invoking the interceptor
		setField(field.getField(), point.getTarget(), modelValue);
	}
	
	private void setField(final Field field, final Object model, final Object modelValue) {
		try {
			field.setAccessible(true);
			field.set(model, modelValue);
			
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
			
		} finally {
			field.setAccessible(false);
			
		}
	}
	
	private WebItem getWebItem(FieldSignature field) {
		return field.getField().getAnnotation(WebItem.class);
	}
	
	@SuppressWarnings("unchecked")
	private IConverter<?> loadConverter(final FieldSignature field, final WebItem webItem, final SelenideElement webElement) {
		// look for a better fitting converter
		IConverter<?> converter = null;
		if (DefaultConverter.class.equals(webItem.converter())) {
			converter = Registry.findConverter(field.getFieldType(), webElement);
			
		} else {
			try {
				converter = webItem.converter().newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
		
		return converter;
	}
	
	public SelenideElement getWebElement(WebItem webItem) {
		final By selector;
		if (StringUtils.isNotEmpty(webItem.id())) {
			selector = By.id(webItem.id());
			
		} else if (StringUtils.isNotEmpty(webItem.css())) {
			selector = By.cssSelector(webItem.css());
			
		} else if (StringUtils.isNotEmpty(webItem.xpath())) {
			selector = By.xpath(webItem.xpath());
			
		} else if (StringUtils.isNotEmpty(webItem.name())) {
			selector = By.name(webItem.name());
			
		} else {
			throw new RuntimeException("No matching selector defined");
		}
		
		return $(selector);
	}
	
}
