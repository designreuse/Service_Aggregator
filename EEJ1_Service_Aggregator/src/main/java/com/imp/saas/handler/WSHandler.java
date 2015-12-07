package com.imp.saas.handler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.imp.saas.config.MultitenancyProperties;
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

	@Value("spring.multitenancy.driver-class-name")
	private String driverClassName;

	@Value("process.db.sqlserver.driver.url")
	private String sqlServerDriverName;

	@Autowired
	MultitenancyProperties multitenancyProperties;

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
	 * 
	 * @return
	 * @throws ConfigExceptions
	 */
	public Map<String, DataSourceProperties> updateDatasourceMap()
			throws ConfigExceptions {

		multitenancyProperties.setDatasourceMap();
		return multitenancyProperties.getDatasourceMap();
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	/**
	 * @return the sqlServerDriverName
	 */
	public String getSqlServerDriverName() {
		return sqlServerDriverName;
	}

	/**
	 * @param sqlServerDriverName
	 *            the sqlServerDriverName to set
	 */
	public void setSqlServerDriverName(String sqlServerDriverName) {
		this.sqlServerDriverName = sqlServerDriverName;
	}

	/**
	 * 
	 * @param dbName
	 * @param dbUserName
	 * @param dbPassword
	 * @param dbHostName
	 * @param dbPort
	 * @return
	 */
	/*
	 * public Map<String, DataSourceProperties> updateDatasourceMap(String
	 * dbName, String dbUserName, String dbPassword, String dbHostName, String
	 * dbPort) {
	 * 
	 * Map<String, DataSourceProperties> datasourceMap = multitenancyProperties
	 * .getDatasourceMap(); DataSourceProperties dataSourceProperties = new
	 * DataSourceProperties(); dataSourceProperties.setUrl(sqlServerDriverName +
	 * dbHostName + ":" + dbPort + ";databaseName=" + dbName);
	 * dataSourceProperties.setUsername(dbUserName);
	 * dataSourceProperties.setPassword(dbPassword);
	 * dataSourceProperties.setDriverClassName(driverClassName);
	 * 
	 * datasourceMap.put(dbName, dataSourceProperties);
	 * multitenancyProperties.setDatasourceMap(datasourceMap); return
	 * datasourceMap; }
	 */
}
