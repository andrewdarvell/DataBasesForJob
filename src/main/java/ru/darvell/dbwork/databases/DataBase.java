package ru.darvell.dbwork.databases;

import java.sql.PreparedStatement;
import java.util.Map;

/**
 * Класс выполнет всю основную работу с бд
 */
//todo - Закомментировать
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

	/**
	 * <p>Устанавливает параметры для работы с БД</p>
	 * @param parameters Список параметр в виде структуры
	 * @return true в случае успеха
	 */
	Boolean setParametersFromConf(Map<String,String> parameters);
	Boolean connect();
	//todo - Написать реализацию потом проверки на соединение
	Boolean isConnected();
	PreparedStatement getPreparedStatement(String query);
	void disconnect();
	//todo - Написать реализацию поучения строки поключения
	String getConnectionString();
}
