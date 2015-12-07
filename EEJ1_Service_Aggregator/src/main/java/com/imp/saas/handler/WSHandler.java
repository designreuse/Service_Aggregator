package com.imp.saas.handler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.imp.saas.exception.ConfigExceptions;
import com.imp.saas.exception.DatabaseCustomException;
import com.imp.saas.util.ScriptRunner;

/**
 * Class responsible for creating database at runtime
 * 
 * @author rakesh.singhania
 *
 */
@Component
@Configuration
@PropertySource("classpath:application.properties")
@ConfigurationProperties("spring.multitenancy")
public class WSHandler {

	@Autowired
	private Environment env;

	@Autowired
	ApplicationContext appContext;

	private List<String> urls;

	private static final Logger LOGGER = Logger.getLogger(WSHandler.class);

	/**
	 * This method is used to create database for a Tenant when he register with
	 * fecilitator
	 * 
	 * @param dbName
	 * @param dbUserName
	 * @param dbPassword
	 * @param dbURL
	 * @return
	 * @throws DatabaseCustomException
	 */
	public String createDatabase(String dbName, String dbUserName,
			String dbPassword, String dbURL) throws DatabaseCustomException {

		Connection connection = null;
		Statement statement = null;
		int result = -1;
		try {
			Class.forName(env.getProperty("database.driver"));

			LOGGER.debug("Database driver name : "
					+ env.getProperty("database.driver"));

			connection = DriverManager.getConnection(dbURL, dbUserName,
					dbPassword);
			statement = connection.createStatement();
			// Create Database
			result = statement.executeUpdate(env
					.getProperty("database.create.command") + " " + dbName);

			LOGGER.debug("Database Create Command Name : "
					+ env.getProperty("database.create.command"));

			LOGGER.debug("result of 'CREATE DATABASE '" + dbName + " is "
					+ result);

			statement.close();
			connection.close();
			dbURL = dbURL + "/" + dbName;
			connection = DriverManager.getConnection(dbURL, dbUserName,
					dbPassword);

			// Read SQL Script file
			File file = new File(env.getProperty("database.script.file.path"));

			LOGGER.debug("Database script file path : "
					+ env.getProperty("database.create.command"));

			// Initialize object for ScripRunner
			ScriptRunner sr = new ScriptRunner(connection, false, false);

			// Give the input file to Reader
			Reader reader = new BufferedReader(new FileReader(file));

			// Exctute script
			sr.runScript(reader);
			connection.close();
		} catch (Exception e) {
			throw new DatabaseCustomException(
					"Unable to create database connection. " + e.getMessage());
		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				throw new DatabaseCustomException(
						"Unable to close database connection. "
								+ e.getMessage());
			}
		}

		return "Database created successfully";
	}

	/**
	 * This method is used to create entry in property file when tenant would
	 * register with fecilitator
	 * 
	 * @param dbName
	 * @param dbUserName
	 * @param dbPassword
	 * @param dbHostName
	 * @param dbPort
	 */
	public String createEntryforTenantInPropertyFile(String dbName,
			String dbUserName, String dbPassword, String dbHostName,
			String dbPort) throws ConfigExceptions {
		try {

			String urlKey = new StringBuffer()
					.append("spring.multitenancy.urls[").append(urls.size())
					.append("]").toString();
			String urlValue = new StringBuffer()
					.append("jdbc:jtds:sqlserver://").append(dbHostName)
					.append(":").append(dbPort).append(";")
					.append("databaseName=").append(dbName).toString();

			String dbUserNameKey = new StringBuffer()
					.append("spring.multitenancy.usernames[")
					.append(urls.size()).append("]").toString();
			String dbUserNameValue = new StringBuffer(dbUserName).toString();

			String dbPasswordKey = new StringBuffer()
					.append("spring.multitenancy.password[")
					.append(urls.size()).append("]").toString();
			String dbPasswordValue = new StringBuffer(dbPassword).toString();

			Resource resource = appContext
					.getResource("classpath:application.properties");

			Properties prop = new Properties();
			prop.setProperty(urlKey, urlValue);
			prop.setProperty(dbUserNameKey, dbUserNameValue);
			prop.setProperty(dbPasswordKey, dbPasswordValue);

			prop.store(new FileWriter(resource.getFile().getName(), true),
					"DB Config file");
			System.out.println(resource.getFile().getName()
					+ " written successfully");

			LOGGER.info("Entry for tenant written in property file");
			return "Entry created successfully";
		} catch (IOException e) {
			throw new ConfigExceptions(e.getMessage());
		}
	}

	public List<String> getUrls() {
		return urls;
	}

	public void setUrls(List<String> urls) {
		this.urls = urls;
	}
}