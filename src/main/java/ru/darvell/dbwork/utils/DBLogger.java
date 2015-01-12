package ru.darvell.dbwork.utils;

import org.apache.log4j.Logger;

/**
 * Для логов поудобнее
 */
public class DBLogger {

	private static Logger log = Logger.getLogger(DBLogger.class.getName());

	public static void printInfo(String message){
		log.info(message);
	}

	public static void printError(String message){
		log.error(message);
	}

}
