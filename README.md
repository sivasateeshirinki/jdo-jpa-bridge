Reference Documentation
===============
1. Introduction
----
The JDO-JPA-Bridge provides an API to developers to implement the single application which will store the data in either NoSQL Data Store provided by Google while running in the Google App Engine or in SQL Data Stores of different vendors like MySQL, Oracle, PostgreSQL etc., while running in the Apache Tomcat Server. Behind this API, JDO is used to perform the data operations of Google Data Store and Hibernate JPA is used for SQL Data Stores. 

2. Design  
-----
The user wants to store the data in either Relational Data Stores and Google Data Stores which is a cloud data store of Google.  Hibernate a well known ORM is used for Relational Data Store, the same cannot be used for Google Data Stores. So, alternatively selected the JDO ORM works under Data Nucleus Platform. Hibernate and JDO differ in style of coding. Thus, we designed an API to give a common interface to work with Hibernate and JDO in a single application. 

3. Getting Started
-----
We noticed that, this API is specifically designed by considering the Spring Web Frame Work. So the Spring Web Frame Work terminology is used. The following steps guide you to start working with this API.
    
I.Configuration of AppConfigurationServlet
--------------------
The AppConfigurationServlet must be configured in web.xml file. It was designed to fetch the server information, enhance the byte code of entities and configure the ORM file. 
For Example:
```xml
<servlet>
    			<servlet-name>AppConfigurationServlet</servlet-name>
                    <Servlet-class>
 	com.iss.datastore.servlet.AppConfigurationServlet
                    </servlet-class>
    			<init-param>
      				<param-name>hibernateConfig</param-name>
      				<param-value>hibernate-config.xml</param-value>
    			</init-param>
    			<init-param>
      				<param-name>jdoConfig</param-name>
      				<param-value>jdo-config.xml</param-value>
    			</init-param>
    			<init-param>
      				<param-name>springApplicationContext</param-name>
      				<param-value>spring-servlet.xml</param-value>
    			</init-param>
    			<init-param>
      				<param-name>entityBasePackage</param-name>
      				<param-value>com.app.entities</param-value>
    			</init-param>
    			<load-on-startup>1</load-on-startup>
  </servlet>
```
i.	hibernate-config.xml which specifies the database connection beans of spring that is integrated with Hibernate ORM.

ii.	Jdo-config.xml which specifies the database connection beans of spring that is integrated with Data Nucleus JDO ORM for Google Data Store.

iii.	spring-servlet.xml which is the Spring Application Context file prescribed by Spring Web Frame Work.

iv.	com.app.entities is the package name that contains Entity Classes (Model Classes) used in the application.
    If the Application is deployed in the Google App Engine, the AppConfigurationSevlet will configure the spring-servlet.xml with the jdo-config.xml and if deployed in Apache Tomcat, it will configure with hibernate-config.xml.  To achieve the spring Autowired annotation support for SessionFactory of Hibernate ORM and PersistenceManagerFactory of JDO ORM, NullPersistenceManagerFactory and NullSessionFactory should be used. The jdo-config.xml must contain an entry of NullSessionFactory as
    
```xml
<bean id="sessionFactory" class = "com.iss.datastore.NullSessionFactory"/>
```
And hibernate-config.xml must contain an entry of NullPersistenceManagerFactory as
```xml
<bean id="persistenceManagerFactory" 
classs = "com.iss.datastore.NullPersistenceManagerFactory"/>
```
Here the ‘sessionFactory’ and ‘persistenceManagerFactory’ are Autowired annotation qualified bean id’s of SessionFactory and PersistenceManagerFactory respectively.
    
II. Configuration of DataStoreManagerFactory
--------
The DataStoreManagerFactory is configured in spring-servlet.xml. For Example:
```xml
<bean id="dataStoreManagerFactory”  
	class="com.iss.datastore.DataStoreManagerFactory">
	    		<property name="entityBasePackage">
				<value>com.app.entities</value>
	 		</property>
</bean>
```
III. Defining entity classes
-----------
The entity classes can be defined using the annotation of both JDO and Hibernate JPA. It is necessary to have an attention while defining the Entity because there are certain limitations on the use of annotations.

i.Simple Entity
```java
package com.app.entities;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
@PersistenceCapable(detachable="true")
@Table(name = "EMPLOYEE")
public class Employee {
  	@PrimaryKey  // JDO annotation to denote id 
@Id		// JPA annotation to denote id
@Column(name = "id") // JPA annotation 
   	@Persistent //JDO annotation to denote that the field should be persist
  	private String id;
   	@Column(name = "first_name")
@Persistent
   	private String firstName;
   	@Column(name = "last_name")
@Persistent
   	private String lastName;
   	@Column(name = "salary")
@Persistent
   	private int salary;
       // Getters and Setters
}
```
ii. Entity with one to one relation ship
```java
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PrimaryKey;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
@PersistenceCapable(detachable="true")
@Table(name = "EMPLOYEE")
public class Employee {
  	@PrimaryKey  // JDO annotation to denote id 
@Id		// JPA annotation to denote id
@Column(name = "id") // JPA annotation 
   	@Persistent //JDO annotation to denote that the field should be persist
  	private String id;
   	@Column(name = "first_name")
@Persistent
   	private String firstName;
   	@Column(name = "last_name")
@Persistent
   	private String lastName;
   	@Column(name = "salary")
@Persistent
   	private int salary;
	@OneToOne(cascade = CascadeType.ALL)
    	@PrimaryKeyJoinColumn
@NotPersistent /*It should be present to prevent the persistence of        
child field by the JDO itself, but it will be persist separately using some logic*/
private Address address;
       // Getters and Setters
}
  // the child entity can be defined as simple entity class.
```
  
IV. Data Access Operations
------
The JDO-JPA Bridge API provides a simple way of implementing the Data Access Operations

i.SAVE Operation
```java
@Autowired
private SessionFactory sessionFactory;
@Autowired
private PersistenceManagerFactory persistenceManagerFactory;
@Autowired
private DataStoreManagerFactory dataStoreManagerFactory;
public void addEmployee(Employee employee) throws KeyViolationException {
DataStoreManager dm = dataStoreManagerFactory.getDataStoreManager();
	dm.registerFactory(sessionFactory,persistenceManagerFactory);
	dm.save(employee);
}
```
ii.	Read operation
```java
public List<Employee> getEmployeeList() {
DataStoreManager dm = dataStoreManagerFactory.getDataStoreManager();
	dm.registerFactory(sessionFactory,persistenceManagerFactory);
	dm.createQuery("from Employee");
	return dm.list();
}
```
iii.	Read operation using where clause 
```java
public List<Employee> getEmployeeList() {
DataStoreManager dm = dataStoreManagerFactory.getDataStoreManager();
	dm.registerFactory(sessionFactory,persistenceManagerFactory);
	dm.createQuery("from Employee where salary>5000");
	return dm.list();
}
```
iV.	Read operation using where clause with Named Parameter
```java
public List<Employee> getEmployeeList() {
DataStoreManager dm = dataStoreManagerFactory.getDataStoreManager();
	dm.registerFactory(sessionFactory,persistenceManagerFactory);
	dm.createQuery("from Employee where salary>:salary");
	dm.setProperty("salary", new Integer(5000));
	return dm.list();
}
```
v. Update operation 
```java
public void updateEmployee(Employee employee) {
DataStoreManager dm = dataStoreManagerFactory.getDataStoreManager();
	dm.registerFactory(sessionFactory,persistenceManagerFactory);
	dm.update(employee);
}
```
vi. Delete operation 

```java
public void deleteEmployee(String id) {
DataStoreManager dm = dataStoreManagerFactory.getDataStoreManager();
	dm.registerFactory(sessionFactory,persistenceManagerFactory);
	Employee e = (Employee) dm.load(Employee.class, id);
dm.delete(employee);
}
```
4. Limitations 
-------------
I. Limitations on Defining Entity Classes
---------------
i.	The @Entity Annotations should not be present on entity classes. It will be added at runtime while the                application deploying in the Apache Tomcat. For JDO ORM the entity classes should be enhanced by the Data             Nucleus Enhancer. Data Nucleus Enhancer designed for both JDO and JPA which are works under the Data Nucleus          Platform. If @Entity annotation presents, data nucleus enhancer will do the misleading operations or raise            some errors.

ii.	Only one to one relationship is implemented for Google Data Store. So, @OneToOne annotation is used to denote         the one to one relationship. On child field @NotPersistent annotations should be used.
    
II. Limitations on Query Language
-------------------
Hibernate Query Language (HQL) should be used for defining query. Only the following Query Plans should be used 

i.	Simple Query 
```
Syntax: “FROM <Entity>” 
Example: “FROM Employee”
```
ii.	Query with WHERE clause:  Here only one condition should be applied and only Named Parameter should be used.  
```
Syntax: “FROM <Entity> WHERE <Condition>”
Example: “FROM Employee WHERE salary>5000”
```
5. Extensibility
-----
i.	This API provides only one to one relation, so we can extend this app to provide one to many, many to one and many to many relations too. 

ii.	 We can extend this API to provide left, right, inner and full join operations.

iii.	We can extend this API to get more Query Plans provided by HQL.

iv.	We can extend this API to support more annotations of JDO and JPA.

v.	We can extend the exception handling mechanism.   
