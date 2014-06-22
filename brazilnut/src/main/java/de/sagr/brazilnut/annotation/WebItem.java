package de.sagr.brazilnut.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import de.sagr.brazilnut.converter.DefaultConverter;
import de.sagr.brazilnut.converter.IConverter;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface WebItem {

	String name() default "";
	
	String id() default "";
	
	String css() default "";
	
	String xpath() default "";
	
	Class<? extends IConverter<?>> converter() default DefaultConverter.class;
	
}
