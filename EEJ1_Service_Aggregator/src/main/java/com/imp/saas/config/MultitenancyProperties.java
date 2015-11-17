package com.imp.saas.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("spring.multitenancy")
public class MultitenancyProperties
{

  Map<String, DataSourceProperties> datasourceMap;
  private List<String> urls;
  private List<String> usernames;
  private List<String> passwords;
  @Value("spring.multitenancy.driver-class-name")
  private String driverClassName;

  @PostConstruct
  public void setDatasourceMap()
  {
    datasourceMap = new HashMap<>();
    for (int index = 0; index < urls.size(); index++)
    {
      String url = urls.get(index);
      String username = usernames.get(index);
      String password = passwords.get(index);
      DataSourceProperties dataSourceProperties = new DataSourceProperties();
      dataSourceProperties.setUrl(url);
      dataSourceProperties.setUsername(username);
      dataSourceProperties.setPassword(password);
      dataSourceProperties.setDriverClassName(driverClassName);
      String tenant = url.split("=")[1];
      datasourceMap.put(tenant, dataSourceProperties);
    }
  }
  
  public List<String> getPassword()
  {
    return passwords;
  }

  public void setPassword(List<String> passwords)
  {
    this.passwords = passwords;
  }

  /**
   * @return the urls
   */
  public List<String> getUrls()
  {
    return urls;
  }

  /**
   * @return the usernames
   */
  public List<String> getUsernames()
  {
    return usernames;
  }

  /**
   * @param urls the urls to set
   */
  public void setUrls(List<String> urls)
  {
    this.urls = urls;
  }

  /**
   * @param usernames the usernames to set
   */
  public void setUsernames(List<String> usernames)
  {
    this.usernames = usernames;
  }

  /**
   * @return the datasourceMap
   */
  public Map<String, DataSourceProperties> getDatasourceMap()
  {
    return datasourceMap;
  }

  public String getDriverClassName()
  {
    return driverClassName;
  }

  public void setDriverClassName(String driverClassName)
  {
    this.driverClassName = driverClassName;
  }
}