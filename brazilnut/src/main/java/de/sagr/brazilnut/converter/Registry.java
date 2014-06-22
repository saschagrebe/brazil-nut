package de.sagr.brazilnut.converter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

import com.codeborne.selenide.SelenideElement;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class Registry {

	private static final Registry INSTANCE = new Registry();
	
	private final Map<ConverterDescriptor, Class<? extends IConverter>> registry = new HashMap<>();
	
	private Registry() {
		Reflections reflections = new Reflections("");
		
		Set<Class<? extends IConverter>> converterClasses = reflections.getSubTypesOf(IConverter.class);
		for (Class<? extends IConverter> nextConverter : converterClasses) {
			if (DefaultConverter.class.equals(nextConverter))  {
				continue;
			}
			
			final Class attributeClass = getTargetAttributeClass(nextConverter);
			final ConverterDescriptor descriptor = new ConverterDescriptor(attributeClass);
			registry.put(descriptor, nextConverter);
		}
	}
	
	private Class getTargetAttributeClass(Class<? extends IConverter> nextConverter) {
		for (Type nextInterface : nextConverter.getGenericInterfaces()) {
			if (nextInterface instanceof ParameterizedType) {
				ParameterizedType nextParameterizedType = (ParameterizedType) nextInterface;
				if (IConverter.class.equals(nextParameterizedType.getRawType())) {
					return (Class) nextParameterizedType.getActualTypeArguments()[0];
				}
			}
		}
		
		return null;
	}
	
	public static <MODEL> IConverter<MODEL> findConverter(Class<MODEL> attributeClass, SelenideElement webElement) {
		return INSTANCE.findConverterInternal(attributeClass, webElement);
	}
	
	private <MODEL> IConverter<MODEL> findConverterInternal(Class<MODEL> attributeClass, SelenideElement webElement) {
		Class<? extends IConverter> converterClass = getConverter(attributeClass, webElement);
		
		// when there is no specific converter take a more general one
		if (converterClass == null) {
			converterClass = getConverter(attributeClass, null);
		}
		
		// when no general converter is found take the defautl converter
		if (converterClass == null) {
			converterClass = DefaultConverter.class;
		}
		
		try {
			return (IConverter<MODEL>) converterClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
	
	private Class<? extends IConverter> getConverter(Class<?> attributeClass, SelenideElement webElement) {
		final ConverterDescriptor descriptor = new ConverterDescriptor(attributeClass, webElement);
		if (registry.containsKey(descriptor)) {
			return registry.get(descriptor);
			
		} else {
			return null;
			
		}
	}
}
