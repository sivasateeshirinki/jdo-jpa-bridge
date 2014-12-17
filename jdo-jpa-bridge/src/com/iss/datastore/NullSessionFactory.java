package com.iss.datastore;

import java.io.Serializable;
import java.sql.Connection;
import java.util.Map;
import java.util.Set;

import javax.naming.NamingException;
import javax.naming.Reference;

import org.hibernate.Cache;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionBuilder;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.StatelessSessionBuilder;
import org.hibernate.TypeHelper;
import org.hibernate.engine.spi.FilterDefinition;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.metadata.CollectionMetadata;
import org.hibernate.stat.Statistics;
/**
 * This class implements the Session Factory.
 * <p>It is used as a NullSessionFactory while the application is runnning in the Google App Engine.
 * @author Siva Sateesh Irinki
 *
 */
public class NullSessionFactory implements SessionFactory {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Reference getReference() throws NamingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void close() throws HibernateException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean containsFetchProfileDefinition(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void evict(Class arg0) throws HibernateException {
		// TODO Auto-generated method stub

	}

	@Override
	public void evict(Class arg0, Serializable arg1) throws HibernateException {
		// TODO Auto-generated method stub

	}

	@Override
	public void evictCollection(String arg0) throws HibernateException {
		// TODO Auto-generated method stub

	}

	@Override
	public void evictCollection(String arg0, Serializable arg1)
			throws HibernateException {
		// TODO Auto-generated method stub

	}

	@Override
	public void evictEntity(String arg0) throws HibernateException {
		// TODO Auto-generated method stub

	}

	@Override
	public void evictEntity(String arg0, Serializable arg1)
			throws HibernateException {
		// TODO Auto-generated method stub

	}

	@Override
	public void evictQueries() throws HibernateException {
		// TODO Auto-generated method stub

	}

	@Override
	public void evictQueries(String arg0) throws HibernateException {
		// TODO Auto-generated method stub

	}

	@Override
	public Map<String, ClassMetadata> getAllClassMetadata() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map getAllCollectionMetadata() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cache getCache() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClassMetadata getClassMetadata(Class arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClassMetadata getClassMetadata(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CollectionMetadata getCollectionMetadata(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Session getCurrentSession() throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set getDefinedFilterNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FilterDefinition getFilterDefinition(String arg0)
			throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SessionFactoryOptions getSessionFactoryOptions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statistics getStatistics() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypeHelper getTypeHelper() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isClosed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Session openSession() throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatelessSession openStatelessSession() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatelessSession openStatelessSession(Connection arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SessionBuilder withOptions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StatelessSessionBuilder withStatelessOptions() {
		// TODO Auto-generated method stub
		return null;
	}

}