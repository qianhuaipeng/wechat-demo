package com.eastrobot.robotdev.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
	private static Properties prop = null;
	
	static {
		load();
	}
	
	private static void load(){
		InputStream inputStream = PropertiesUtil.class.getResourceAsStream( "/app.properties");
		Properties properties = null;
		try {
			properties = new Properties();
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		prop =  properties;
	}
	
	public static String getValue(String key){
		if (prop == null) {
			load();
		}
		return prop.getProperty(key);
	}
	
	public static void main(String[] args) {
		System.out.println(getValue("sso.encoding"));
		
	}
}
