package com.iss.datastore;

import java.util.Collection;
import java.util.Properties;
import java.util.Set;

import javax.jdo.FetchGroup;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.datastore.DataStoreCache;
import javax.jdo.listener.InstanceLifecycleListener;
import javax.jdo.metadata.JDOMetadata;
import javax.jdo.metadata.TypeMetadata;
/**
 * This class implements the PersistenceManagerFactory.
 * <p>It is used as a NullPersistenceManagerFactory while application is running in the Apache Tomcat Sever
 * @author Siva Sateesh Irinki
 * 
 */
public class NullPersistenceManagerFactory implements PersistenceManagerFactory {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void addFetchGroups(FetchGroup... arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addInstanceLifecycleListener(InstanceLifecycleListener arg0,
			Class[] arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getConnectionDriverName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getConnectionFactory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getConnectionFactory2() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getConnectionFactory2Name() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getConnectionFactoryName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getConnectionURL() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getConnectionUserName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getCopyOnAttach() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public DataStoreCache getDataStoreCache() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getDatastoreReadTimeoutMillis() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getDatastoreWriteTimeoutMillis() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getDetachAllOnCommit() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public FetchGroup getFetchGroup(Class arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set getFetchGroups() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getIgnoreCache() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getMapping() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypeMetadata getMetadata(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getMultithreaded() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getNontransactionalRead() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getNontransactionalWrite() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getOptimistic() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PersistenceManager getPersistenceManager() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PersistenceManager getPersistenceManager(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PersistenceManager getPersistenceManagerProxy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPersistenceUnitName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Properties getProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getReadOnly() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getRestoreValues() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getRetainValues() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getServerTimeZoneID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTransactionIsolationLevel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTransactionType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isClosed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public JDOMetadata newMetadata() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void registerMetadata(JDOMetadata arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeAllFetchGroups() {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeFetchGroups(FetchGroup... arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeInstanceLifecycleListener(InstanceLifecycleListener arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setConnectionDriverName(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setConnectionFactory(Object arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setConnectionFactory2(Object arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setConnectionFactory2Name(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setConnectionFactoryName(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setConnectionPassword(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setConnectionURL(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setConnectionUserName(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setCopyOnAttach(boolean arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDatastoreReadTimeoutMillis(Integer arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDatastoreWriteTimeoutMillis(Integer arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDetachAllOnCommit(boolean arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setIgnoreCache(boolean arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setMapping(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setMultithreaded(boolean arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setName(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setNontransactionalRead(boolean arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setNontransactionalWrite(boolean arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setOptimistic(boolean arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPersistenceUnitName(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setReadOnly(boolean arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setRestoreValues(boolean arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setRetainValues(boolean arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setServerTimeZoneID(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTransactionIsolationLevel(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTransactionType(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public Collection<String> supportedOptions() {
		// TODO Auto-generated method stub
		return null;
	}

}
