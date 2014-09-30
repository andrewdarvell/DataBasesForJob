package ru.darvell.dbwork.databases;

import java.sql.PreparedStatement;
import java.util.Map;

public interface DataBase {
	Boolean setParameters(String dbName, String login, String pass, String host, String port);
	Boolean setParametersFromConf(Map<String,String> parameters);
	Boolean connect();
	Boolean isConnected();
	PreparedStatement getPreparedStatement(String query);
	void disconnect();

	String getConnectionString();
}
