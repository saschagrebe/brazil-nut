package de.sagr.brazilnut;

public class ObjectMapper {
	
	public static <T> T getInstance(Class<T> objectClass) {
		T object;
		try {
			object = objectClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		// TODO initialize with interceptors
		
		return object;
	}
}
