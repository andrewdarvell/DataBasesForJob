package ru.darvell.dbwork;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ConfigReader {
	private static Logger log = Logger.getLogger(ConfigReader.class);

	static Boolean fileExist(String fileName){
		File file = new File(fileName);
		if(file.exists()){
			return true;
		}else {
			return false;
		}
	}

	public static Map<String,Map<String, String>> readFile(String fileName){
		if(fileExist(fileName)){
			try {
				Map<String,Map<String, String>> result = new HashMap();
				BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
				String line;
				while ((line = bufferedReader.readLine()) != null) {
					String params[] = line.split(";");
					String parName = "";
					Map<String, String> map = new HashMap<>();
					for (int i = 0; i < params.length; i++) {
						String param[] = params[i].split(":");
						if (param[0].equals("parname")){
							parName = param[1];
						}else {
							map.put(param[0], param[1]);
						}
					}
					result.put(parName,map);
				}
				return result;

			}catch (Exception e){
				log.error(e.toString());
			}
		}else {
			log.info(fileName);
			log.info("No file");
		}
		return null;
	}
}
