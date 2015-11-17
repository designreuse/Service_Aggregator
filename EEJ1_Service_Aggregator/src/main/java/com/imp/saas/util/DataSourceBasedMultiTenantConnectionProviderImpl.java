package com.imp.saas.util;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.imp.saas.config.DataSourceCreater;
import com.imp.saas.config.MultitenancyProperties;

@Component
public class DataSourceBasedMultiTenantConnectionProviderImpl extends
		AbstractDataSourceBasedMultiTenantConnectionProviderImpl {

	private static final long serialVersionUID = 8168907057647334460L;
	private static final String DEFAULT_TENANT_ID = "ServicePlatform";

	@Autowired
	MultitenancyProperties multitenancyProperties;

	private Map<String, DataSource> map;

	@Autowired
	ApplicationContext applicationContext;

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

		System.out.println("Datasources map: ============= " + map);
	}

	@Override
	protected DataSource selectAnyDataSource() {
		return map.get(DEFAULT_TENANT_ID);
	}

	@Override
	protected DataSource selectDataSource(String tenantIdentifier) {
		return map.get(tenantIdentifier);
	}
}
