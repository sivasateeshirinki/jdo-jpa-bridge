package com.iss.datastore;

import java.io.Serializable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jdo.PersistenceManagerFactory;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
/**
 * This class implements the DataStoreManager.
 * <p>It uses the Hibernate FrameWork to perform CRUD Operations 
 * @author Siva Sateesh Iriki
 * @version 1.0
 */
public class RelationalDataStoreManager<T> implements DataStoreManager<T> {
	private SessionFactory sessionFactory;
	private Map <String,T>map=new HashMap<String,T>();
	private Session session;
	private Query queryObj;
	public Session getSession() {
    	createSession();
		return session;
	}
    
	public void setSession(Session session) {
		this.session = session;
	}
	public void createSession()
	{
		session=sessionFactory.openSession();
		this.setSession(session);
	}
    public void closeSession()
    {
    	this.session.close();
    }
	public Query getQueryObj() {
		return queryObj;
	}

	public void setQueryObj(Query queryObj) {
		this.queryObj = queryObj;
	}

	@Override
	public void save(T t) {
		Session session=this.getSession();
	    Transaction tx=session.beginTransaction();
	    session.save(t);
		tx.commit();
		this.closeSession();
	}
	
	@Override
	public List<?> list() {
		Query queryObj=this.getQueryObj();
		if(!map.isEmpty())
			queryObj.setProperties(map);
	    List <?>list= queryObj.list();
	    this.map.clear();
	    this.closeSession();
	    return list;
	}
	@Override
	public void update(T t) {
		Session session =this.getSession();
		Transaction tx = session.beginTransaction();
		session.update(t);
		tx.commit();
		this.closeSession();
	}

	@Override
	public void delete(T t) {
		Session session=this.getSession();
		Transaction tx=session.beginTransaction();
	    session.delete(t);
		tx.commit();
		this.closeSession();
	}

	@Override
	public Object load(Class<?> c, T t) {
		Session session=this.getSession();
		Object obj= session.load(c, (Serializable)t);
		this.closeSession();
		return obj;
		
	}

	@Override
	public void createQuery(String queryString) {
		Session session=this.getSession();
		Query queryObj=session.createQuery(queryString);
	    this.setQueryObj(queryObj);
	}
	public void setProperty(String param,T val)
	{
		map.put(param, val);
	}

	@Override
	public void registerFactory(SessionFactory sessionFactory,
			PersistenceManagerFactory persistenceManagerFactory) {
	 this.sessionFactory=sessionFactory;
		
	}
}
 