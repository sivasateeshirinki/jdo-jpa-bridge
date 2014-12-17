package com.iss.datastore;

import com.iss.datastore.properties.ServerProperties;

/**
 * This class implements the logic to create an instance and return Data Store Manager based on the server in which the application is running.
 * <p>If the application running in the Google App Engine,it creates an instance of GoogleDataStoreManager and return it.<br> 
 * If the application running in the Apache Tomcat,it creates an instance of RelationalDataStoreManager and return it.</p>
 * @author Siva Sateesh Irinki
 * @version 1.0
 */
public class DataStoreManagerFactory {
	private DataStoreManager dataStoreManager;
	
	/**
	 * Set the base package of the entity classes
	 * @param entityBasePackage base package of the entity classes 
	 */
	public void setEntityBasePackage(String entityBasePackage) {
		QueryParser.setEntityBasePackage(entityBasePackage);
	}
	/**
	 * Get the DataStoreManager
	 * @return instance of  DataStoreManager
	 */
	public DataStoreManager getDataStoreManager() {
		return dataStoreManager;
	}
	/**
	 * Set the DataStoreManager
	 * @param dataStoreManager instance of DataStoreManager 
	 */
	public void setDataStoreManager(DataStoreManager dataStoreManager) {
		this.dataStoreManager = dataStoreManager;
	}
	
	public DataStoreManagerFactory()
	{
		buildDataStoreManager();
	}
	/**
	 *  This method has a logic to create an instance of the DataStoreManager based on the server.
	 */
	public  void buildDataStoreManager(){
		        ServerProperties serverProperties=new ServerProperties();
				String serverName=serverProperties.getServerName();
			    System.out.println(serverName);
				if(serverName.equals(Constants.APACHE_TOMCAT))
				{
				    setDataStoreManager(new RelationalDataStoreManager());
				}
				else if(serverName.equals(Constants.GOOGLE_APP_ENGINE))
				{
					setDataStoreManager(new GoogleDataStoreManager());
					
				}
				else 
				{
					setDataStoreManager(null);
				}
	}
}
