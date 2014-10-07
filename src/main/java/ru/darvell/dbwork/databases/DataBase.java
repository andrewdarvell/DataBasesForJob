package ru.darvell.dbwork.databases;

import java.sql.PreparedStatement;
import java.util.Map;

/**
 * Класс выполнет всю основную работу с бд
 */

public interface DataBase {
	/**
	 * <p>Уставнавливает параметры для работы с БД</p>
	 * @param dbName название базы данных
	 * @param login имя пользователя
	 * @param pass пароль
	 * @param host хост
	 * @param port порт
	 * @return true в случае успеха
	 */
	Boolean setParameters(String dbName, String login, String pass, String host, String port);
	Boolean setParametersFromConf(Map<String,String> parameters);
	Boolean connect();
	Boolean isConnected();
	PreparedStatement getPreparedStatement(String query);
	void disconnect();

	String getConnectionString();
}
