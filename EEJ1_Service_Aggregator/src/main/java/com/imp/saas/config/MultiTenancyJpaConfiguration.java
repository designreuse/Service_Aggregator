package com.imp.saas.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.hibernate.MultiTenancyStrategy;
import org.hibernate.cfg.Environment;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import com.imp.saas.domain.Employee;
import com.imp.saas.exception.ConfigExceptions;
/**
 * 
 * Class responsible for setting properties for connection 
 * this call actually build datasource for the configuration provided
 * @author rakesh.singhania
 *
 */
@Configuration
@EnableConfigurationProperties(JpaProperties.class)
@ConfigurationProperties("spring.multitenancy")
public class MultiTenancyJpaConfiguration
{

  @Autowired
  private JpaProperties jpaProperties;

  @Autowired
  private MultiTenantConnectionProvider multiTenantConnectionProvider;

  @Autowired
  MultitenancyProperties multitenancyProperties;

  @Autowired
  private CurrentTenantIdentifierResolver currentTenantIdentifierResolver;
  
  @Value("spring.multitenancy.dialect")
  private String dialect;
  

  @Value("spring.multitenancy.masterDb")
  private String masterDb;


  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(
    EntityManagerFactoryBuilder builder) throws ConfigExceptions
  {
    Map<String, Object> hibernateProps = new LinkedHashMap<String, Object>();
    hibernateProps.putAll(jpaProperties.getHibernateProperties(DataSourceCreater
      .getDataSourceFromDataSourceProperties(multitenancyProperties.getDatasourceMap().get(masterDb))));

    hibernateProps.put(Environment.MULTI_TENANT, MultiTenancyStrategy.DATABASE);
    hibernateProps.put(Environment.MULTI_TENANT_CONNECTION_PROVIDER, multiTenantConnectionProvider);
    hibernateProps.put(Environment.MULTI_TENANT_IDENTIFIER_RESOLVER,
      currentTenantIdentifierResolver);
    hibernateProps.put(Environment.POOL_SIZE, 10);
    hibernateProps.put(Environment.DIALECT, dialect);

    return builder
      .dataSource(
        DataSourceCreater.getDataSourceFromDataSourceProperties(multitenancyProperties
          .getDatasourceMap().get(masterDb))).packages(Employee.class.getPackage().getName())
      .properties(hibernateProps).jta(false).build();
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
   * @return the dialect
   */
  public String getDialect()
  {
    return dialect;
  }

  /**
   * @param dialect the dialect to set
   */
  public void setDialect(String dialect)
  {
    this.dialect = dialect;
  }

}
