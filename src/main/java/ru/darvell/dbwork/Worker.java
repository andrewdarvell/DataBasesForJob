package ru.darvell.dbwork;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import ru.darvell.dbwork.databases.DataBase;
import ru.darvell.dbwork.databases.impl.MySQLImpl;
import ru.darvell.dbwork.databases.impl.OracleImpl;

import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *<p> Прослойка между приложением и классом для работы с БД</p>
 *<p>Приложение должно работать именно с этим классом
 * Сначала инициализируем конфигурационным файлом
 * </p>
 * <p>
 * 	if (Worker.initConfig("etc/config.csv")){...}
 *</p>
 */

public class Worker {

	private static Logger log = Logger.getLogger(Worker.class);

	static Map<String,Map<String,String>> config = new HashMap<>();
	static Map<String,DataBase> bases = new HashMap<>();

	/**
	 * <p>Загружает конфиг из файла</p>
	 *
	 * @param fileName Имя файла конфигурации
	 * @return true если инициализация прошла успешно, false если файл не найден или ошибка в конфиге
	 */
	public static boolean initConfig(String fileName){
		if ((config = ConfigReader.readFile(fileName))!= null){
			Set<String> keys = config.keySet();
			for (String key:keys){
				Map<String, String> map = config.get(key);
				if (map.get("type").equals("main")){
					try{
						DOMConfigurator.configure(map.get("log_file_conf"));
						return true;
					}catch(Exception e){
						log.error("problem with log4j config file");
					}
				}
			}
		}
		return false;
	}

	/**
	 * <p>Устанавливает соединение с БД</p>
	 * <p>Берет данные для соединения с БД из параметров описанных в конфигурационном файле</p>
	 * @param confName имя параметра в конфиге ('parname')
	 * @return true в случае успешного соедиения, false - если возникает ошибка (сама ошибка в лог файле)
	 */
	public static boolean dbConnect(String confName){
		try{
			Map<String,String> confMap = config.get(confName);
			if (confMap != null){
				if (confMap.get("type").equals("db")){
					DataBase dataBase = null;
					switch (confMap.get("base_type")){
						case "mysql": dataBase = new MySQLImpl();
								break;
						case "orcl": dataBase = new OracleImpl();
								break;
						}
					if (dataBase != null) {
						dataBase.setParametersFromConf(confMap);
						dataBase.connect();
						bases.put(confName,dataBase);
						log.info("connected "+confName);
						return true;
					}else {
						log.error("Not suppoted DataBase");
						return false;
					}
				}else {
					log.error("Config type not DB");
				}
			}else {
				log.error("No config with name "+confName);
			}

		}catch(Exception e){
			log.error(e.toString());
		}
		return false;
	}

	/**
	 *<p>Запрос к базе данных</p>
	 * @param confName имя параметра в конфиге ('parname')
	 * @param qery запрос к базе данных
	 * @return PreparedStatement или null в сдучае ошибок
	 */
	public static PreparedStatement getDbStatement(String confName, String qery){
		try{
			return bases.get(confName).getPreparedStatement(qery);
		}catch (Exception e){
			log.error(e.toString());
		}
		return null;
	}

	public static void commit(String confName){
		try {
			bases.get(confName).commit();
		}catch (Exception e){
			log.error(e.toString());
		}
	}

	/**
	 * <p>Закрывает соединение</p>
	 * <p>Закрывает соединение с выбранной базой данных</p>
	 * @param confName имя параметра в кон
	 */
	public static void closeConnection(String confName){
		try{
			bases.get(confName).disconnect();
			log.info(confName + " disconnected");
		}catch (Exception e){
			log.error(e.toString());
		}
	}

}
