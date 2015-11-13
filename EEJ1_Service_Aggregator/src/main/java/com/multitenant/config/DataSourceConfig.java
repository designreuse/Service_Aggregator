package com.multitenant.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig
{

  @Autowired
  MultitenancyProperties multitenancyProperties;

  @Bean(name = "datasource")
  public DataSource getDatasource()
  {
    DataSourceBuilder factory = DataSourceBuilder
      .create(multitenancyProperties.getDatasourceMap().get("Tenant1").getClassLoader())
      .driverClassName("net.sourceforge.jtds.jdbc.Driver").username("sp").password("impetus@123")
      .url("jdbc:jtds:sqlserver://192.168.219.51:1433;databaseName=Tenant1");
    return factory.build();
  }
}
