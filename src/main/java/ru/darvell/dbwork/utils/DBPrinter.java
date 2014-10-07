package ru.darvell.dbwork.utils;

import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>Класс для вывода результатов запроса</p>
 */
public class DBPrinter {

	private static Logger log = Logger.getLogger(DBPrinter.class);
	private static String defaultSeparator = "\t";

	/**
	 * <p>Вывод на консоль с параметрами по умолчанию<p/>
	 * @param resultSet Данные для вывода
	 */
	public static void print(ResultSet resultSet){
		printCore(resultSet, null);
	}

	/**
	 * <p>Вывод на консоль с заданным разделителем<p/>
	 * @param resultSet Данные для вывода
	 * @param sep Разделитель
	 */
	public static void print(ResultSet resultSet,String sep){
		printCore(resultSet, sep);
	}

	/**
	 * Установить раздедитель по умолчанию
	 * @param sep Разделитель
	 */
	public static void setDefaultSeparator(String sep){
		defaultSeparator = sep;
	}

	/**
	 * <p>Основной метод печати</p>
	 * <p>Выводит в консоль результаты запроса с задаными параметрами</p>
	 * @param resultSet Результат зароса
	 * @param separator Разделитель
	 */
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
