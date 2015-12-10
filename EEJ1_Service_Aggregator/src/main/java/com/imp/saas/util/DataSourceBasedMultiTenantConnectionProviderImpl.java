package com.imp.saas.util;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.imp.saas.config.DataSourceCreater;
import com.imp.saas.config.MultitenancyProperties;
import com.imp.saas.exception.ConfigExceptions;
import com.imp.saas.ws.bean.DBResponse;

/**
 * Creating DataSourceProperties for the DB connection of different different
 * tenant We are putting DataSourceProperties in a map with "tenantid" as
 * identifier
 * 
 * @author rakesh.singhania
 *
 */
@Component
@ConfigurationProperties("spring.multitenancy")
public class DataSourceBasedMultiTenantConnectionProviderImpl extends
		AbstractDataSourceBasedMultiTenantConnectionProviderImpl {

	private static final long serialVersionUID = 8168907057647334460L;
	private static final String DEFAULT_TENANT_ID = "ServicePlatform";

	@Autowired
	MultitenancyProperties multitenancyProperties;

	@Autowired
	Environment env;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(DataSourceBasedMultiTenantConnectionProviderImpl.class);

	private Map<String, DataSource> map;

	@Autowired
	ApplicationContext applicationContext;

	/**
	 * setting Map<String, DataSource> map properties key is tenant Id in the
	 * Map Once all properties are set everytime database details are loaded
	 * from this map
	 * 
	 * @throws ConfigExceptions
	 *
	 */
	@PostConstruct
	public void load() throws ConfigExceptions {

		Map<String, DataSourceProperties> datasourceMap = multitenancyProperties
				.getDatasourceMap();
		map = new HashMap<>();
		for (String key : datasourceMap.keySet()) {
			map.put(key, DataSourceCreater
					.getDataSourceFromDataSourceProperties(datasourceMap
							.get(key)));
		}

		LOGGER.info("Datasources map: ============= " + map);
	}

	/**
	 * This helps in selecting data source on basis of tenant id.
	 */
	@Override
	protected DataSource selectAnyDataSource() {
		return map.get(DEFAULT_TENANT_ID);
	}

	@Override
	protected DataSource selectDataSource(String tenantIdentifier) {

		try {
			load();
		} catch (ConfigExceptions e) {
			LOGGER.error("exception :" + e.getMessage());
		}
		return map.get(tenantIdentifier);
	}

	/**
	 * @param dbProfileData
	 * @return
	 */
	public DBResponse[] getTenantDatabaseMetadatDetails() {
		RestTemplate rt = new RestTemplate();
		rt.getMessageConverters().add(new StringHttpMessageConverter());
		String uri = new String(env.getProperty("tenant.database.metadata.uri"));
		DBResponse[] response = null;
		try {
			response = (DBResponse[]) rt.postForObject(uri, null,
					DBResponse[].class);
		} catch (ResourceAccessException e) {

		}
		return response;

	}
}
