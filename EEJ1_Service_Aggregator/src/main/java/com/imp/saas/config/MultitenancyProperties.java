package com.imp.saas.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.imp.saas.exception.ConfigExceptions;
import com.imp.saas.ws.bean.DBResponse;

/**
 * Set properties of DB in Map so that they can be configured. Reading all
 * properties from properties file for each tenant.
 * 
 * @author rakesh.singhania
 *
 */
@ConfigurationProperties("spring.multitenancy")
public class MultitenancyProperties {

	Map<String, DataSourceProperties> datasourceMap;
	private List<String> urls;
	private List<String> usernames;
	private List<String> passwords;

	@Value("spring.multitenancy.driver-class-name")
	private String driverClassName;

	@Value("spring.multitenancy.masterDb")
	private String masterDb;

	@Value("spring.multitenancy.masterDbURL")
	private String masterDbURL;

	@Value("spring.multitenancy.masterDbPassword")
	private String masterDbPassword;

	@Value("spring.multitenancy.masterDbUserName")
	private String masterDbUserName;

	@Value("process.db.sqlserver.driver.url")
	private String sqlServerDriverName;

	@Autowired
	Environment env;

	@PostConstruct
	public void setDatasourceMap() throws ConfigExceptions {
		datasourceMap = new HashMap<>();
		/*
		 * for (int index = 0; index < urls.size(); index++) { String url =
		 * urls.get(index); String username = usernames.get(index); String
		 * password = passwords.get(index); DataSourceProperties
		 * dataSourceProperties = new DataSourceProperties();
		 * dataSourceProperties.setUrl(url);
		 * dataSourceProperties.setUsername(username);
		 * dataSourceProperties.setPassword(password);
		 * dataSourceProperties.setDriverClassName(driverClassName); String
		 * tenant = url.split("=")[1]; datasourceMap.put(tenant,
		 * dataSourceProperties); }
		 */
		DBResponse[] tenantMetadatList = getTenantDatabaseMetadatDetails();
		for (DBResponse dbResponse : tenantMetadatList) {
			DataSourceProperties dataSourceProperties = new DataSourceProperties();

			dataSourceProperties.setUrl(sqlServerDriverName
					+ dbResponse.getHostName() + ":"
					+ dbResponse.getPortNumber() + ";databaseName="
					+ dbResponse.getDbName());
			dataSourceProperties.setUsername(dbResponse.getDbUserName());
			dataSourceProperties.setPassword(dbResponse.getDbPassword());
			dataSourceProperties.setDriverClassName(driverClassName);
			datasourceMap.put(dbResponse.getDbName(), dataSourceProperties);
		}

		System.out.println(tenantMetadatList);

		// Setting master Db content in case No tenant is there.
		DataSourceProperties masterDataSourceProperties = new DataSourceProperties();
		masterDataSourceProperties.setUrl(masterDbURL);
		masterDataSourceProperties.setUsername(masterDbUserName);
		masterDataSourceProperties.setPassword(masterDbPassword);
		masterDataSourceProperties.setDriverClassName(driverClassName);
		datasourceMap.put(masterDb, masterDataSourceProperties);

	}

	/**
	 * @param dbProfileData
	 * @return
	 */
	public DBResponse[] getTenantDatabaseMetadatDetails()
			throws ConfigExceptions {
		RestTemplate rt = new RestTemplate();
		rt.getMessageConverters().add(new StringHttpMessageConverter());
		String uri = new String(env.getProperty("tenant.database.metadata.uri"));
		DBResponse[] response = null;
		try {
			response = (DBResponse[]) rt.postForObject(uri, null,
					DBResponse[].class);
		} catch (ResourceAccessException e) {
			throw new ConfigExceptions(e.getMessage());
		}
		return response;

	}

	public List<String> getPasswords() {
		return passwords;
	}

	public void setPasswords(List<String> passwords) {
		this.passwords = passwords;
	}

	public String getMasterDb() {
		return masterDb;
	}

	public void setMasterDb(String masterDb) {
		this.masterDb = masterDb;
	}

	public List<String> getPassword() {
		return passwords;
	}

	public void setPassword(List<String> passwords) {
		this.passwords = passwords;
	}

	/**
	 * @return the urls
	 */
	public List<String> getUrls() {
		return urls;
	}

	/**
	 * @return the usernames
	 */
	public List<String> getUsernames() {
		return usernames;
	}

	/**
	 * @param urls
	 *            the urls to set
	 */
	public void setUrls(List<String> urls) {
		this.urls = urls;
	}

	/**
	 * @param usernames
	 *            the usernames to set
	 */
	public void setUsernames(List<String> usernames) {
		this.usernames = usernames;
	}

	/**
	 * @return the datasourceMap
	 */
	public Map<String, DataSourceProperties> getDatasourceMap() {
		return datasourceMap;
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	/**
	 * @return the masterDbURL
	 */
	public String getMasterDbURL() {
		return masterDbURL;
	}

	/**
	 * @param masterDbURL
	 *            the masterDbURL to set
	 */
	public void setMasterDbURL(String masterDbURL) {
		this.masterDbURL = masterDbURL;
	}

	/**
	 * @return the masterDbPassword
	 */
	public String getMasterDbPassword() {
		return masterDbPassword;
	}

	/**
	 * @param masterDbPassword
	 *            the masterDbPassword to set
	 */
	public void setMasterDbPassword(String masterDbPassword) {
		this.masterDbPassword = masterDbPassword;
	}

	/**
	 * @return the masterDbUserName
	 */
	public String getMasterDbUserName() {
		return masterDbUserName;
	}

	/**
	 * @param masterDbUserName
	 *            the masterDbUserName to set
	 */
	public void setMasterDbUserName(String masterDbUserName) {
		this.masterDbUserName = masterDbUserName;
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
}