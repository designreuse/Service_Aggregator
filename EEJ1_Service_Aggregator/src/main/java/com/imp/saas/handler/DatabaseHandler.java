package com.imp.saas.handler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.imp.saas.util.ScriptRunner;

public class DatabaseHandler {

	public String createDatabase(String dbName, String dbUserName,
			String dbPassword, String dbURL) {

		Connection connection = null;
		Statement statement = null;
		int result = -1;
		try {
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			connection = DriverManager.getConnection(dbURL, dbUserName,
					dbPassword);
			statement = connection.createStatement();
			// Create Database
			result = statement.executeUpdate("CREATE DATABASE " + dbName);
			System.out.println("result of 'CREATE DATABASE '" + dbName + " is "
					+ result);
			statement.close();
			connection.close();
			dbURL = dbURL + "/" + dbName;
			connection = DriverManager.getConnection(dbURL, dbUserName,
					dbPassword);

			// Read SQL Script file
			File file = new File(
					"C:\\Users\\skgupta\\Desktop\\utility\\utility\\src\\ServicePlatform.sql");
			// Initialize object for ScripRunner
			ScriptRunner sr = new ScriptRunner(connection, false, false);
			// Give the input file to Reader
			Reader reader = new BufferedReader(new FileReader(file));
			// Exctute script
			sr.runScript(reader);
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return "success";
	}

}
