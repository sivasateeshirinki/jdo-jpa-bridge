package com.iss.datastore.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import com.iss.datastore.MetaData;
import com.iss.datastore.properties.FetchProperties;
/**
 * This class use the Java Reflection API to do the required operations
 * @author Siva Sateesh Irinki
 * @version 1.0
 */
public class ReflectionUtil {
	/**
	 * Merges the parent and child lists.
	 */
	public static List<?> merge(List<?> parentList, List<?> childList,
			FetchProperties fetchProperties) {
		try {
			Class<?> parentClass = Class.forName(fetchProperties
					.getParentEntity());
			Class<?> childClass = Class.forName(fetchProperties
					.getChildEntityField().getType().getCanonicalName());

		    Field parentKey = MetaData.getKeyField(parentClass);
			Field childKey = MetaData.getKeyField(childClass);

			Method parentKeyGetterMethod = ReflectionUtil.getterMethod(
					parentClass, parentKey.getName(), new Class[] {});
			Method childKeyGetterMethod = ReflectionUtil.getterMethod(
					childClass, childKey.getName(), new Class[] {});

			Method parentSetterMethod = ReflectionUtil.setterMethod(
					parentClass, fetchProperties.getChildEntityField()
							.getName(), new Class[] { childClass });

			for (int i = 0; i < parentList.size(); i++) {
				Object parentObject = parentList.get(i);
				String parentKeyValue = (String) parentKeyGetterMethod
						.invoke(parentObject, new Object[] {});
				for (int j = 0; j < childList.size(); j++) {
					Object childObject = childList.get(j);
					String childKeyValue = (String) childKeyGetterMethod
							.invoke(childObject, new Object[] {});
					if (parentKeyValue.equals(childKeyValue)) {
						parentSetterMethod.invoke(parentObject,
								new Object[] { childList.get(j) });
						childList.remove(childObject);
						break;
					}
				}
			}
			return parentList;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	/**
	   * @param c Class Instance
	   * @param name name of the field
	   * @param parameterTypes argument types of the method
	   * @return The reflection method of field setter 
	   */
	public static Method getterMethod(Class<?> c, String name,
			Class<?> parameterTypes[]) {
		if (name != null && name.trim() != "") {
			String getter = MetaData.getGetterMethodName(name);
			try {
				return c.getMethod(getter, parameterTypes);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
  /**
   * 
   * @param c Class Instance
   * @param name name of the field
   * @param parameterTypes argument types of the method
   * @return The reflection method of field getter 
   */
	public static Method setterMethod(Class<?> c, String name,
			Class<?> parameterTypes[]) {
		if (name != null && name.trim() != "") {
			String setter = MetaData.getSetterMethodName(name);
			try {
				return c.getMethod(setter, parameterTypes);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
    /**
     * Get the Key Field Value
     */
	public static Object getKeyValue(Object obj) {
		String keyName = MetaData.getKeyField(obj.getClass()).getName();
		try {
			System.out.println(keyName);
			Method m = ReflectionUtil.getterMethod(obj.getClass(), keyName,
					new Class[] {});
			String key=(String)m.invoke(obj, new Object[] {});
			return key;
		} catch (Exception ex) {
			return null;
		}
	}
    /**
     * Get the Id Filed value
     */
	public static Object getIdValue(Object obj) {
		String idName = MetaData.getIDField(obj.getClass()).getName();
		try {
			Method m = ReflectionUtil.getterMethod(obj.getClass(), idName,
					new Class[] {});
			return m.invoke(obj, new Object[] {});
		} catch (Exception ex) {
			return null;
		}
	}
	/**
	 * Set the child object in the parent object
	 */
public static void setChildObject(Object parentObject,Object childObject,String childFieldName)
{
	Method parentSetterMethod = ReflectionUtil.setterMethod(
			parentObject.getClass(), childFieldName, new Class[] { childObject.getClass() });
	try {
		parentSetterMethod.invoke(parentObject,new Object[] { childObject });
	} catch (IllegalAccessException e) {
		e.printStackTrace();
	} catch (IllegalArgumentException e) {
		e.printStackTrace();
	} catch (InvocationTargetException e) {
		e.printStackTrace();
	}
}
/**
 * Get the child object
 */
	public static Object getChildObject(Object parentObject) {
		try {
			Field childField = MetaData.getFieldByAnnotation(
					parentObject.getClass(), "OneToOne");
			if (childField != null) {
				Method childGetterMethod = ReflectionUtil.getterMethod(
						parentObject.getClass(), childField.getName(),
						new Class[] {});
				Object childObject = (Object) childGetterMethod.invoke(
						parentObject, new Object[] {});
				
				if (childObject != null) {
					return childObject;
				}
			}
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			
			e.printStackTrace();
		}
		return null;
	}
}
