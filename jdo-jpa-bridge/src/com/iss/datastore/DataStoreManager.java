package com.iss.datastore;

import java.util.List;

import javax.jdo.PersistenceManagerFactory;

import org.hibernate.SessionFactory;

import com.iss.datastore.exception.KeyViolationException;

/**
 * This interface consists of abstract methods to implement the basic CRUD
 * Operations
 * 
 * @author Siva Sateesh Irinki
 * @version 1.0
 * @param <T>
 *            generic field
 */
public interface DataStoreManager<T> {
	/**
	 * Saves the data object in the data store
	 */
	public void save(T t) throws KeyViolationException;

	/**
	 * @param sessionFactory
	 *            Instance of SessionFactory
	 * @param persistenceManagerFactory
	 *            Instance of PersistenceManagerFactory
	 */
	public void registerFactory(SessionFactory sessionFactory,
			PersistenceManagerFactory persistenceManagerFactory);

	/**
	 * Returns the obtained result list
	 * 
	 * @return Result List
	 */
	public List<?> list();

	/**
	 * Updates the Data Object
	 */
	public void update(T t);

	/**
	 * Deletes the data object
	 */
	public void delete(T t);

	/**
	 * Loads the data object
	 * 
	 * @param c
	 *            Entity Class
	 * @param t
	 *            Key of the Entity Class
	 * @return Return the loaded data object
	 */
	public Object load(Class<?> c, T t);

	/**
	 * @param prop
	 *            property name
	 * @param val
	 *            property value
	 */
	public void setProperty(String prop, T val);

	/**
	 * Creates a new Query Object
	 * 
	 * @param query
	 *            The HQL query to create Query Object
	 */
	public void createQuery(String queryString);
}
