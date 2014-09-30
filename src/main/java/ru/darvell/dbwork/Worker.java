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

public class Worker {

	private static Logger log = Logger.getLogger(Worker.class);

	static Map<String,Map<String,String>> config = new HashMap<>();
	static Map<String,DataBase> bases = new HashMap<>();

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

	public static PreparedStatement getDbStatement(String confName, String qery){
		try{
			return bases.get(confName).getPreparedStatement(qery);
		}catch (Exception e){
			log.error(e.toString());
		}
		return null;
	}

	public static void closeConnection(String confName){
		try{
			bases.get(confName).disconnect();
		}catch (Exception e){
			log.error(e.toString());
		}
	}

}
