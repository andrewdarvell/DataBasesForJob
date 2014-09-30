package ru.darvell.dbwork;


import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import ru.darvell.dbwork.databases.DataBase;
import ru.darvell.dbwork.databases.impl.MySQLImpl;
import ru.darvell.dbwork.databases.impl.OracleImpl;
import ru.darvell.dbwork.utils.DBPrinter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {

	private static Logger log = Logger.getLogger(Main.class.getName());

	public static void main(String[] args){



		String mySqlLocal = "mysql_local";
		String aaa = "111";
		if (Worker.initConfig("etc/config.csv")){
			if(Worker.dbConnect(mySqlLocal)){
				try {
					PreparedStatement ps = Worker.getDbStatement(mySqlLocal, "SELECT * FROM city");
					ResultSet resultSet = ps.executeQuery();
//					while (resultSet.next()) {
//						log.info(resultSet.getString("name"));
//					}
					DBPrinter.print(resultSet);
					resultSet.close();
					ps.close();
					Worker.closeConnection(mySqlLocal);
				}catch (Exception e){
					log.error(e.toString());
				}
			}else {
				log.error("Error to connect");
			}
		}else {
			log.error("Error init config");
		}
	}
}
