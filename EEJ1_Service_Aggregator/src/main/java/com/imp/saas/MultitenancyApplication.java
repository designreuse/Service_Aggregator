package com.imp.saas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.imp.saas.config.MultitenancyProperties;

@SpringBootApplication
@EnableConfigurationProperties(MultitenancyProperties.class)
public class MultitenancyApplication
{

  public static void main(String[] args)
  {
    SpringApplication.run(MultitenancyApplication.class, args);
  }
}
