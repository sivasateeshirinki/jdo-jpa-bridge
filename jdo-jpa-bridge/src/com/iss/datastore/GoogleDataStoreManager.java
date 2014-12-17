package com.iss.datastore;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import org.hibernate.SessionFactory;

import com.iss.datastore.exception.KeyViolationException;
import com.iss.datastore.properties.FetchProperties;
import com.iss.datastore.util.ReflectionUtil;
/**
 * This class implements the DataStoreManager.
 * <p>It uses the JDO FrameWork to perform CRUD Operations 
 * @author Siva Sateesh Iriki
 * @version 1.0
 */
public class GoogleDataStoreManager<T> implements DataStoreManager<T> {
	private PersistenceManagerFactory persistenceManagerFactory;
	private PersistenceManager persistenceManager;
	private Query queryObj;
	private Map<String, T> map = new HashMap<String, T>();
	private FetchProperties fetchProperties;

	public FetchProperties getFetchProperties() {
		return fetchProperties;
	}

	public void setFetchProperties(FetchProperties fetchProperties) {
		this.fetchProperties = fetchProperties;
	}

	public Query getQueryObj() {
		return queryObj;
	}

	public void setQueryObj(Query queryObj) {
		this.queryObj = queryObj;
	}

	public PersistenceManager getPersistenceManager() {
		createPersistenceManager();
		return persistenceManager;
	}

	public void setPersistenceManager(PersistenceManager persistenceManager) {
		this.persistenceManager = persistenceManager;
	}
	
	public void createPersistenceManager() {
		setPersistenceManager(persistenceManagerFactory.getPersistenceManager());
	}

	@Override
	public void save(T t) throws KeyViolationException {
		PersistenceManager pm = this.getPersistenceManager();
		Object keyValue = ReflectionUtil.getKeyValue(t);
		try {
			pm.getObjectById(t.getClass(), keyValue);
			throw new KeyViolationException("Key already exists");
		} catch (JDOObjectNotFoundException ex) {
			pm.makePersistent(t);
			Object childObject = ReflectionUtil.getChildObject(t);
			if (childObject != null) {
				pm.makePersistent(childObject);
			}
		} finally {

			pm.close();
		}
	}

	@Override
	public List<?> list() {
		PersistenceManager pm=this
		.getPersistenceManager();
		Query queryObj = this.getQueryObj();
		Field childEntityField = this.getFetchProperties()
				.getChildEntityField();
		List<?> list = null;
		List<?> list1 = null;
		List<?> list2 = null;
		try {
			if (map.isEmpty()) {
				list1 = (List<?>) queryObj.execute();
			} else {
				list1 = (List<?>) queryObj.executeWithMap(map);
			}
			if (childEntityField != null) {
				list2 = (List<?>) pm
						.newQuery(
								Class.forName(childEntityField.getType()
										.getCanonicalName())).execute();
				list = ReflectionUtil.merge(list1, list2,
						this.getFetchProperties());
			} else {
				list = list1;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		finally
		{
			pm.close();
		}
		
		return list;
	}

	@Override
	public void update(T t) {
		PersistenceManager pm = persistenceManagerFactory
				.getPersistenceManager();
		pm.makePersistent(t);
		Object childObject = ReflectionUtil.getChildObject(t);
		if (childObject != null) {
			pm.makePersistent(childObject);
		}
		pm.close();
	}

	@Override
	public void delete(T t) {
		PersistenceManager pm = persistenceManagerFactory
				.getPersistenceManager();
		Object childObject = ReflectionUtil.getChildObject(t);
		if (childObject != null) {
			pm.deletePersistent(t);
			pm.deletePersistent(childObject);
		} else {
			pm.deletePersistent(t);
		}
		pm.close();
	}

	@Override
	public Object load(Class<?> c, T t) {
		PersistenceManager pm = persistenceManagerFactory
				.getPersistenceManager();
		Object obj = pm.getObjectById(c, t);
		Object detachObj = pm.detachCopy(obj);
		Field childField = MetaData.getFieldByAnnotation(c, "OneToOne");
		if (childField != null) {
			try {
				Class<?> childClass = Class.forName(childField.getType()
						.getCanonicalName());
				Object childObj = pm.getObjectById(childClass, t);
				Object detachChild = pm.detachCopy(childObj);
				ReflectionUtil.setChildObject(detachObj, detachChild,
						childField.getName());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (JDOObjectNotFoundException e) {
			}
		}
		pm.close();
		return detachObj;
	}

	@Override
	public void setProperty(String prop, T val) {
		map.put(prop, val);
	}

	@Override
	public void createQuery(String queryString) {
		FetchProperties fetchProperties = QueryParser.parseQuery(queryString);
		Query queryObj = this.getPersistenceManager().newQuery(
				fetchProperties.getFormatedQueryString());
		this.setQueryObj(queryObj);
		this.setFetchProperties(fetchProperties);
	}

	@Override
	public void registerFactory(SessionFactory sessionFactory,
			PersistenceManagerFactory persistenceManagerFactory) {
		this.persistenceManagerFactory = persistenceManagerFactory;
	}
}
