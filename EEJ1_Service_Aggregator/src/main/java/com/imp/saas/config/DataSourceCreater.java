package com.imp.saas.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;

/**
 * 
 * Creates factory of connection by the provided datasource properties
 * @author rakesh.singhania
 *
 */
public class DataSourceCreater
{

  public static DataSource getDataSourceFromDataSourceProperties(
    DataSourceProperties dataSourceProperties)
  {
    DataSourceBuilder factory = DataSourceBuilder.create(dataSourceProperties.getClassLoader())
      .driverClassName(dataSourceProperties.getDriverClassName())
      .username(dataSourceProperties.getUsername()).password(dataSourceProperties.getPassword())
      .url(dataSourceProperties.getUrl());
    return factory.build();
  }

}
