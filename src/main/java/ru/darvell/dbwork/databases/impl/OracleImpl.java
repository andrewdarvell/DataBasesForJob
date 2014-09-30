package ru.darvell.dbwork.databases.impl;

import org.apache.log4j.Logger;
import ru.darvell.dbwork.databases.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class OracleImpl implements DataBase{

	String dbName;
	String login;
	String pass;
	String host;
	String port;

	String connString;

	Connection connection;

	private static Logger log = Logger.getLogger(OracleImpl.class.getName());

	public OracleImpl() {
	}

	public OracleImpl(String dbName, String login, String pass, String host, String port) {
		this.dbName = dbName;
		this.login = login;
		this.pass = pass;
		this.host = host;
		this.port = port;
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
		return null;
	}

	@Override
	public Boolean connect() {
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch (Exception e){
			log.error(e.toString());
			return false;
		}
		try{
			connection = DriverManager.getConnection(getConnectionString(), login, pass);
			connection.setAutoCommit(false);
			log.info("Connected to database Oracle");
			return true;
		}catch(Exception e){
			log.error(e.toString());
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
		}catch (Exception e){
			log.error(e.toString());
		}
		return null;
	}

	@Override
	public void disconnect() {
		try {
			connection.close();
		} catch (SQLException e) {
			log.error(e.toString());
		}
	}

	@Override
	public String getConnectionString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("jdbc:oracle:thin:@//")
				.append(host)
				.append(":")
				.append(port)
				.append("/")
				.append(dbName);
		return stringBuilder.toString();
	}
}