package com.iss.datastore.servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.iss.datastore.Constants;
import com.iss.datastore.enhanser.Enhancer;

/**
 * The AppConfigurationServlet configure the server information ,enhance the entities 
 * and setup the ORM file
 * @author Siva Sateesh Irinki
 * @version 1.0
 */

public class AppConfigurationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String serverName = "";
	private String serverVersion = "";
	private String webInfPath = "";
	private String springApplicationContext = "";
	private String hibernateConfig = "";
	private String jdoConfig = "";
	private String entityBasePackage = "";

	public AppConfigurationServlet() {
		super();
	}
    /**
     * AppConfigurationSevlet init method
     */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		String serverInfo = config.getServletContext().getServerInfo();
		webInfPath = config.getServletContext().getRealPath("WEB-INF");
		System.out.println(webInfPath);
		int index = serverInfo.indexOf('/');
		serverName = serverInfo.substring(0, index);
		serverVersion = serverInfo.substring(index + 1);
		configureServerInfo();
		entityBasePackage = config.getInitParameter("entityBasePackage").trim();
		if ((entityBasePackage != null || entityBasePackage != "")
				&& serverName.equals(Constants.APACHE_TOMCAT)) {
			this.triggerEnhancer();
		}
		springApplicationContext = config.getInitParameter(
				"springApplicationContext").trim();
		hibernateConfig = config.getInitParameter("hibernateConfig").trim();
		jdoConfig = config.getInitParameter("jdoConfig").trim();
		if ((springApplicationContext != null || springApplicationContext != "")
				&& (hibernateConfig != null || hibernateConfig != "")
				&& (jdoConfig != null || jdoConfig != "")) {
			this.setupORMFile();
		}
	}
    /**
     * This method Configure the Server Information
     */
	public void configureServerInfo() {
		try {
			File f = new File(webInfPath + "/classes/META-INF");
			if (!f.exists()) {
				f.mkdir();
			}
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder;
			docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("ServerInfo");
			doc.appendChild(rootElement);

			Element serverNameElement = doc.createElement("Name");
			serverNameElement.appendChild(doc.createTextNode(serverName));
			rootElement.appendChild(serverNameElement);

			Element versionElement = doc.createElement("Version");
			versionElement.appendChild(doc.createTextNode(serverVersion));
			rootElement.appendChild(versionElement);

			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(f.getAbsolutePath()
					+ "/server_info.xml"));
			transformer.transform(source, result);
		} catch (ParserConfigurationException | TransformerException e) {

			e.printStackTrace();
		}
	}
    /**
     *  This method trigger the enhancer for byte code enhancement 
     */
	public void triggerEnhancer() {
		String packageDirectory = entityBasePackage.replace(".", "/");
		File fileList[] = (new File(webInfPath + "/classes/" + packageDirectory))
				.listFiles();
		Enhancer enhancer = new Enhancer();
		for (File f1 : fileList) {
			int separatorIndex = f1.getName().indexOf(".");
			String className = f1.getName().substring(0, separatorIndex);
			enhancer.enhance(className, entityBasePackage, webInfPath
					+ "/classes");
		}
	}
    /**
     * Setting the ORM File
     */
	public void setupORMFile() {
		try {
			String springAppContextXMlFilePath = webInfPath + "/"
					+ springApplicationContext;
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(springAppContextXMlFilePath);
			Node root = doc.getFirstChild();
			Node importNode = doc.getElementsByTagName("import").item(0);
			if (importNode != null) {
				root.removeChild(importNode);
			}
			Element importElement = doc.createElement("import");
			if (serverName.equalsIgnoreCase(Constants.APACHE_TOMCAT)) {
				importElement.setAttribute("resource", hibernateConfig);
			} else {
				importElement.setAttribute("resource", jdoConfig);
			}
			root.appendChild(importElement);
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(
					springAppContextXMlFilePath));
			transformer.transform(source, result);
		} catch (ParserConfigurationException | TransformerException
				| SAXException | IOException e) {
			e.printStackTrace();
		}
	}
}
