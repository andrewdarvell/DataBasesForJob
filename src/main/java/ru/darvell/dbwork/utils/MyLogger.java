package ru.darvell.dbwork.utils;

import org.apache.log4j.Logger;

/**
 * Created by darvell on 09.01.15.
 */
public class MyLogger {
    public static Logger log = Logger.getLogger(MyLogger.class.getName());

    public static Logger getLogger(){
        return log;
    }

}
