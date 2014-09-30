package ru.darvell.dbwork.utils;

import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DBPrinter {

	private static Logger log = Logger.getLogger(DBPrinter.class);
	private static String defaultSeparator = "\t";

	public static void print(ResultSet resultSet){
		printCore(resultSet, null);
	}

	public static void print(ResultSet resultSet,String sep){
		printCore(resultSet, sep);
	}
	public static void setDefaultSeparator(String sep){
		defaultSeparator = sep;
	}

	static void printCore(ResultSet resultSet, String separator){
		if (separator == null){
			separator = defaultSeparator;
		}
		try {
			System.out.println();
			int count = resultSet.getMetaData().getColumnCount();
			for (int i = 1; i <= count ; i++) {
				System.out.print(resultSet.getMetaData().getColumnName(i)+separator);
			}
			System.out.println();
			while (resultSet.next()){
				for (int i = 1; i <= count ; i++) {
					System.out.print(resultSet.getString(i)+separator);
				}
				System.out.println();
			}

		} catch (SQLException e) {
			log.error(e.getMessage());
		}
	}
}
