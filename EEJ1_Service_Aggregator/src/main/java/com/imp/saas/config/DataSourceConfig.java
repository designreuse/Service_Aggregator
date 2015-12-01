package com.imp.saas.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Default Db setup for multi-tenant environment
 * this is basically master DB for the application 
 * 
 * @author rakesh.singhania
 *
 */
@Configuration
@ConfigurationProperties("spring.multitenancy")
public class DataSourceConfig
{

  @Autowired
  MultitenancyProperties multitenancyProperties;
  
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

  
  @Bean(name = "datasource")
  public DataSource getDatasource()
  {
    DataSourceBuilder factory = DataSourceBuilder
      .create(multitenancyProperties.getDatasourceMap().get(masterDb).getClassLoader())
      .driverClassName(driverClassName).username(masterDbUserName).password(masterDbPassword)
      .url(masterDbURL);
    return factory.build();
  }


  /**
   * @return the driverClassName
   */
  public String getDriverClassName()
  {
    return driverClassName;
  }


  /**
   * @param driverClassName the driverClassName to set
   */
  public void setDriverClassName(String driverClassName)
  {
    this.driverClassName = driverClassName;
  }


  /**
   * @return the masterDb
   */
  public String getMasterDb()
  {
    return masterDb;
  }


  /**
   * @param masterDb the masterDb to set
   */
  public void setMasterDb(String masterDb)
  {
    this.masterDb = masterDb;
  }


  /**
   * @return the masterDbURL
   */
  public String getMasterDbURL()
  {
    return masterDbURL;
  }


  /**
   * @param masterDbURL the masterDbURL to set
   */
  public void setMasterDbURL(String masterDbURL)
  {
    this.masterDbURL = masterDbURL;
  }


  /**
   * @return the masterDbPassword
   */
  public String getMasterDbPassword()
  {
    return masterDbPassword;
  }


  /**
   * @param masterDbPassword the masterDbPassword to set
   */
  public void setMasterDbPassword(String masterDbPassword)
  {
    this.masterDbPassword = masterDbPassword;
  }


  /**
   * @return the masterDbUserName
   */
  public String getMasterDbUserName()
  {
    return masterDbUserName;
  }


  /**
   * @param masterDbUserName the masterDbUserName to set
   */
  public void setMasterDbUserName(String masterDbUserName)
  {
    this.masterDbUserName = masterDbUserName;
  }
}
