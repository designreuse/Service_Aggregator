package com.imp.saas.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("spring.multitenancy")
public class MultitenancyProperties {

	/*
	 * @NestedConfigurationProperty private DataSourceProperties datasource1;
	 * 
	 * @NestedConfigurationProperty private DataSourceProperties datasource2;
	 * 
	 * @NestedConfigurationProperty private DataSourceProperties datasource3;
	 */

	Map<String, DataSourceProperties> datasourceMap;

	private List<String> urls;
	private List<String> usernames;

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

	@PostConstruct
	public void setDatasourceMap() {
		datasourceMap = new HashMap<>();
		for (int i = 0; i < urls.size(); i++) {
			String url = urls.get(i);
			String username = usernames.get(i);
			DataSourceProperties dataSourceProperties = new DataSourceProperties();
			dataSourceProperties.setUrl(url);
			dataSourceProperties.setUsername(username);
			dataSourceProperties.setPassword("impetus@123");
			dataSourceProperties
					.setDriverClassName("net.sourceforge.jtds.jdbc.Driver");
			String tenent = url.split("=")[1];
			datasourceMap.put(tenent, dataSourceProperties);
		}
	}

}