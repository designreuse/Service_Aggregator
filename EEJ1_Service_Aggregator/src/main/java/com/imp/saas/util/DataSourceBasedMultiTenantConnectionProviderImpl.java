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
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.imp.saas.config.DataSourceCreater;
import com.imp.saas.config.MultitenancyProperties;

/**
 * Creating DataSourceProperties for the DB connection of different different tenant 
 * We are putting DataSourceProperties in a map with "tenantid" as identifier
 * @author rakesh.singhania
 *
 */
@Component
public class DataSourceBasedMultiTenantConnectionProviderImpl extends
		AbstractDataSourceBasedMultiTenantConnectionProviderImpl {

	private static final long serialVersionUID = 8168907057647334460L;
	private static final String DEFAULT_TENANT_ID = "ServicePlatform";

	@Autowired
	MultitenancyProperties multitenancyProperties;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceBasedMultiTenantConnectionProviderImpl.class);
	
	private Map<String, DataSource> map;

	@Autowired
	ApplicationContext applicationContext;

	/**
	 * setting Map<String, DataSource> map properties 
	 * key is tenant Id in the Map
	 * Once all properties are set everytime database details are loaded from this map
	 *
	 */
	@PostConstruct
	public void load() {

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
	 *  This helps in selecting data source on basis of tenant id.
	 */
	@Override
	protected DataSource selectAnyDataSource() {
		return map.get(DEFAULT_TENANT_ID);
	}

	@Override
	protected DataSource selectDataSource(String tenantIdentifier) {
		return map.get(tenantIdentifier);
	}
}
