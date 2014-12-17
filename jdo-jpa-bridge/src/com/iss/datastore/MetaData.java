package com.iss.datastore;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * This class is implemented for retrieve the meta data of an entity
 * 
 * @author Siva Sateesh Irinki
 * @version 1.0
 */
public class MetaData {
	/**
	 * Get the getter method name of the field
	 * 
	 * @param fieldName
	 *            Name of the field
	 */
	public static String getGetterMethodName(String fieldName) {
		if (fieldName != null && fieldName.trim() != "") {
			String getter = "get" + fieldName.substring(0, 1).toUpperCase();
			if (fieldName.length() > 1) {
				String tail = fieldName.substring(1);
				getter = getter + tail;
			}
		}
		return null;
	}

	/**
	 * Get the setter method name of the field
	 * 
	 * @param fieldName
	 *            Name of the field
	 */
	public static String getSetterMethodName(String fieldName) {
		if (fieldName != null && fieldName.trim() != "") {
			String setter = "set" + fieldName.substring(0, 1).toUpperCase();
			if (fieldName.length() > 1) {
				String tail = fieldName.substring(1);
				setter = setter + tail;
			}
			return setter;
		}
		return null;
	}

	/**
	 * Get the Field which contains the Annotation 'Id'
	 * 
	 * @param c
	 *            Instance of Class
	 */
	public static Field getIDField(Class<?> c) {
		Field fields[] = c.getDeclaredFields();
		for (Field field : fields) {
			Annotation annotations[] = field.getAnnotations();
			for (Annotation a : annotations) {
				String annotationName = a.annotationType().getSimpleName();
				if (annotationName.equals("Id")) {
					return field;
				}
			}
		}
		return null;
	}

	/**
	 * Get the Field which contains the Annotation 'PrimaryKey'
	 * 
	 * @param c
	 *            Instance of Class
	 */
	public static Field getKeyField(Class<?> c) {
		Field fields[] = c.getDeclaredFields();
		for (Field field : fields) {
			Annotation annotations[] = field.getAnnotations();
			for (Annotation a : annotations) {
				String annotationName = a.annotationType().getSimpleName();
				if (annotationName.equals("PrimaryKey")) {
					return field;
				}
			}
		}
		return null;
	}

	/**
	 * Get the field that has the given Annotation
	 * 
	 * @param c
	 *            Instance Of Class
	 * @param annotationName
	 *            Name of the Annotation
	 */
	public static Field getFieldByAnnotation(Class<?> c, String annotationName) {
		Field fields[] = c.getDeclaredFields();
		for (Field field : fields) {
			Annotation annotations[] = field.getAnnotations();
			for (Annotation a : annotations) {
				if (a.annotationType().getSimpleName().equals(annotationName)) {
					return field;
				}
			}
		}
		return null;
	}
}
