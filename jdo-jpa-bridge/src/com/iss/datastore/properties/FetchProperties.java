package com.iss.datastore.properties;

import java.lang.reflect.Field;
/**
 * This class has the setter and getter methods of useful properties
 * of Query to Fetch result  
 * @author Siva Sateesh Irinki
 * @version 1.0
 */
public class FetchProperties {
	private String parentEntity="";
	private String formatedQueryString="";
	private Field childEntityField=null;
	/**
	 * Get name of parent entity
	 * @return the name of the main entity in the 'FROM' clause of the Query 
	 */
	public String getParentEntity() {
		return parentEntity;
	}
	/**
	 * Set name of parent entity 
	 * @param parentEntity name of the entity 
	 */
	public void setParentEntity(String parentEntity) {
		this.parentEntity = parentEntity;
	}
	/**
	 * Get the entity field which is refer to child entity that 
     * declared in an entity which is refer to parent entity
     * @return <code>childEntityField</code> If exists
     *         <code>null</code> Otherwise.
	 */
    public Field getChildEntityField() {
		return childEntityField;
	}
    /**
     * Set the entity field which is refer to child entity that 
     * declared in an entity which is refer to parent entity
     */
	public void setChildEntityField(Field childEntityField) {
		this.childEntityField = childEntityField;
	}
	/**
	 * Get the formated query of given Hibernate Query. The Formated Query is used 
	 * JDO Framework    
	 */
	public String getFormatedQueryString() {
		return formatedQueryString;
	}
	/**
	 * Set the formated query of given Hibernate Query. The Formated Query is used 
	 * JDO Framework    
	 */
	public void setFormatedQueryString(String formatedQueryString) {
		this.formatedQueryString = formatedQueryString;
	}
}
