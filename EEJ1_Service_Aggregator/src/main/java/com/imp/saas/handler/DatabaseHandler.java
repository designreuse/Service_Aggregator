package com.imp.saas.handler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.imp.saas.util.ScriptRunner;

@Component
@Configuration
@PropertySource("classpath:application.properties")
public class DatabaseHandler
{

  @Autowired
  private Environment env;
  
  private static final Logger LOGGER = Logger.getLogger(DatabaseHandler.class);

  public String createDatabase(String dbName, String dbUserName, String dbPassword, String dbURL)
  {

    Connection connection = null;
    Statement statement = null;
    int result = -1;
    try
    {
      Class.forName(env.getProperty("database.driver"));
      
      LOGGER.debug("Database driver name : " + env.getProperty("database.driver"));
      
      connection = DriverManager.getConnection(dbURL, dbUserName, dbPassword);
      statement = connection.createStatement();
      // Create Database
      result = statement.executeUpdate(env.getProperty("database.create.command") + " " + dbName);
      
      LOGGER.debug("Database Create Command Name : " + env.getProperty("database.create.command"));
      
      LOGGER.debug("result of 'CREATE DATABASE '" + dbName + " is " + result);
      
      statement.close();
      connection.close();
      dbURL = dbURL + "/" + dbName;
      connection = DriverManager.getConnection(dbURL, dbUserName, dbPassword);

      // Read SQL Script file
      File file = new File(env.getProperty("database.script.file.path"));
      
      LOGGER.debug("Database script file path : " + env.getProperty("database.create.command"));
      
      // Initialize object for ScripRunner
      ScriptRunner sr = new ScriptRunner(connection, false, false);
      
      // Give the input file to Reader
      Reader reader = new BufferedReader(new FileReader(file));
      
      // Exctute script
      sr.runScript(reader);
      connection.close();
    }
    catch (Exception e)
    {
    	LOGGER.error("Error : ", e);
    }
    finally
    {
      try
      {
        if (connection != null) connection.close();
      }
      catch (SQLException e)
      {
    	  LOGGER.error("Error : ", e);
      }
    }

    return "Database created successfully";
  }

}
