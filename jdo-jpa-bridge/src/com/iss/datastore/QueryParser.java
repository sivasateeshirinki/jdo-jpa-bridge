package com.iss.datastore;

import java.lang.reflect.Field;
import java.util.StringTokenizer;

import com.iss.datastore.properties.FetchProperties;
/**
 * This class parse the query and converts the Hibernate Query to JDO Query  
 * @author Siva Sateesh Irinki
 * @version 1.0
 */
public class QueryParser {
	private static String entityBasePackage;
	/**
	 * Get the base package of entities
	 */
	public static String getEntityBasePackage() {
		return entityBasePackage;
	}
	/**
	 * Set the base package of entities
	 */
	public static void setEntityBasePackage(String entityBasePackage) {
		QueryParser.entityBasePackage = entityBasePackage;
	}
	/**
	 * This method parse HQL query string 
	 * @param query The Hibernate query string 
	 * @return FetchProperties 
	 */
	public static FetchProperties parseQuery(String query) {
		String entity="";
		String formatedCondition="";
		String cond="";
		FetchProperties fetchProperties = new FetchProperties();
		try{
		StringTokenizer tokenizer = new StringTokenizer(query);
		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
			if(token.equalsIgnoreCase("from"))
			{   
				entity = tokenizer.nextToken();
				fetchProperties.setParentEntity(entityBasePackage+"."+entity);
				Field field=MetaData.getFieldByAnnotation(Class.forName(entityBasePackage+"."+entity), "OneToOne");
			    if(field!=null)
			    {
			    	fetchProperties.setChildEntityField(field);
			    }  
			}
			else if(token.equalsIgnoreCase("where"))
			{
			    cond=query.substring(query.indexOf(token)+5);
				formatedCondition=getParseCondition(cond.trim());
			}
		}
		String partialFormatedQuery = "SELECT "+query.replaceFirst(entity, entityBasePackage+"."
				+ entity);
		String formatedQuery = partialFormatedQuery.replaceFirst(cond, " "+formatedCondition);
		fetchProperties.setFormatedQueryString(formatedQuery);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return fetchProperties;
	}
	/**
	 * @param condition The query condition
	 * @return Formated condition that is equivalent JDO Specification.
	 */
	public static String getParseCondition(String condition) {
		String operators[] = { "<=", ">=", "!=","<",">","=" };
		String formatedCondition="";
				for (String operator : operators) {
					int index = condition.indexOf(operator);
					if (index != -1) {
						String operand1 = condition.substring(0, index);
						String operand2 = condition.substring(index
								+ operator.length());
						if(operator.equals("="))
						{
							formatedCondition=operand1+"=="+operand2;
						}
						else
						{
							formatedCondition=condition;
						}					
					}
				}
		return formatedCondition;
	}
}
