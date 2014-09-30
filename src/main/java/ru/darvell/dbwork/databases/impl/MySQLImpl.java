package ru.darvell.dbwork.databases.impl;

import org.apache.log4j.Logger;
import ru.darvell.dbwork.databases.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class MySQLImpl implements DataBase{

	String dbName;
	String login;
	String pass;
	String host;
	String port;

	String connString;

	Connection connection;

	private static Logger log = Logger.getLogger(MySQLImpl.class.getName());

	public MySQLImpl() {
	}

	public MySQLImpl(String dbName, String login, String pass, String host, String port) {
		this.dbName = dbName;
		this.login = login;
		this.pass = pass;
		this.host = host;
		this.port = port;
	}

	public MySQLImpl(String connString) {
		this.connString = connString;
	}

	@Override
	public Boolean setParameters(String dbName, String login, String pass, String host, String port) {
		this.dbName = dbName;
		this.login = login;
		this.pass = pass;
		this.host = host;
		this.port = port;
		return true;
	}

	@Override
	public Boolean setParametersFromConf(Map<String, String> parameters) {
		try {
			this.dbName = parameters.get("base");
			this.login = parameters.get("login");
			this.pass = parameters.get("password");
			this.host = parameters.get("host");
			this.port = parameters.get("port");
		}catch (Exception e){
			log.error(e.toString());
		}
		return false;
	}


	@Override
	public Boolean connect() {
		try{
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch(ClassNotFoundException classnotfoundexception){
			log.error("Exception: " + classnotfoundexception.toString());
			return false;
		}
		try{
			connection = DriverManager.getConnection(getConnectionString(), login, pass);
			connection.setAutoCommit(false);
			log.info("Connected to database MYSQL");
			return true;

		}catch(Exception e){
			log.error("Exception: " + e.toString());
		}
		return false;
	}

	@Override
	public Boolean isConnected() {
		try {
			return connection.isValid(100);

		} catch (SQLException e) {
			log.error(e.toString());
		}
		return false;
	}

	@Override
	public PreparedStatement getPreparedStatement(String query) {
		try {
			return connection.prepareStatement(query);
		} catch (SQLException e) {
			log.error(e.toString());
		}
		return null;
	}


	@Override
	public void disconnect() {
		try{
			connection.close();
		}catch (Exception e){
			log.error(e.toString());
		}
	}

	@Override
	public String getConnectionString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("jdbc:mysql://")
				.append(host)
				.append(":")
				.append(port)
				.append("/")
				.append(dbName);
		return stringBuilder.toString();
	}


}
