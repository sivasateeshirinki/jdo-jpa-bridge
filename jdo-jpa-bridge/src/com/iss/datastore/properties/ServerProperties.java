package com.iss.datastore.properties;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * This Class set and get the server name and version
 * @author Siva Sateesh Irinki
 * @version 1.0
 */
public class ServerProperties {
	private String serverName = "";
	private String serverVersion = "";
	/**
	 * @return name of the server
	 */
	public String getServerName() {
		return serverName;
	}
    /**
     * @param serverName name of the current running server
     */
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
    /**
     * @return version of the server
     */
	public String getServerVersion() {
		return serverVersion;
	}

	public void setServerVersion(String serverVersion) {
		this.serverVersion = serverVersion;
	}
	public ServerProperties() {
		try {
			InputStream inputstream = getClass().getClassLoader()
					.getResourceAsStream("META-INF/server_info.xml");
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder;

			docBuilder = docFactory.newDocumentBuilder();

			Document doc = docBuilder.parse(inputstream);
			Node nameNode = doc.getElementsByTagName("Name").item(0);
			if (nameNode != null)
				setServerName(nameNode.getTextContent());
			Node versionName = doc.getElementsByTagName("Version").item(0);
			if (versionName != null)
				setServerVersion(versionName.getTextContent());
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}

	}
}
