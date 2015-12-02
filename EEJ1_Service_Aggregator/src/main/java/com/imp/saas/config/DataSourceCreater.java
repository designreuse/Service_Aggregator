package com.imp.saas.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;

import com.imp.saas.exception.ConfigExceptions;

/**
 * 
 * Creates factory of connection by the provided data source properties
 * 
 * @author rakesh.singhania
 *
 */
public class DataSourceCreater {

	public static DataSource getDataSourceFromDataSourceProperties(
			DataSourceProperties dataSourceProperties) throws ConfigExceptions {
		DataSourceBuilder factory = DataSourceBuilder
				.create(dataSourceProperties.getClassLoader())
				.driverClassName(dataSourceProperties.getDriverClassName())
				.username(dataSourceProperties.getUsername())
				.password(dataSourceProperties.getPassword())
				.url(dataSourceProperties.getUrl());
		if (factory == null) {
			throw new ConfigExceptions("Unable to create DataSource.");
		}
		return factory.build();
	}

}
