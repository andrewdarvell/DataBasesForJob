package ru.darvell.dbwork;


import org.apache.log4j.Logger;
import ru.darvell.dbwork.utils.DBLogger;
import ru.darvell.dbwork.utils.DBPrinter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Main {

	private static Logger log = Logger.getLogger(Main.class.getName());

	public static void main(String[] args){



		String mySqlLocal = "bgbilling";
		if (Worker.initConfig("etc/config.csv")){
			if(Worker.dbConnect(mySqlLocal)){
				try {

					String sql = "SELECT dest.title AS title\n " +
							"FROM phone_geographic_code_1 gc, phone_dest_1 dest\n" +
							"WHERE gc.dest_id=dest.id AND gc.code = ?";

					BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("/home/darvell/tmp/tar")));
					String strok;
					while  ((strok = br.readLine()) != null){

						String[] pars = strok.split("\t\t");
						PreparedStatement ps = Worker.getDbStatement(mySqlLocal, sql);
						ps.setString(1, pars[0]);
						ResultSet resultSet = ps.executeQuery();
						while (resultSet.next()){
//							System.out.println(resultSet.getString("title")+";"+pars[1]);
							DBLogger.printInfo(resultSet.getString("title") + ";" + pars[1]);
						}

					}


//					String sql = "SELECT dest.title\n" +
//							"FROM phone_geographic_code_1 gc, phone_dest_1 dest\n" +
//							"WHERE gc.dest_id=dest.id";
//					PreparedStatement ps = Worker.getDbStatement(mySqlLocal, sql);
//					ResultSet resultSet = ps.executeQuery();
//					DBPrinter.print(resultSet);
//					resultSet.close();
//					ps.close();
//					Worker.closeConnection(mySqlLocal);
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
