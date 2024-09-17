/*
 * Copyright (c) 2022 Anh Tester
 * Automation Framework Selenium
 */

package helpers;

import constants.GlobalConstants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Properties;

public class PropertiesHelpers {

	private static Properties properties;
	private static String linkFile;
	private static FileInputStream file;
	private static FileOutputStream out;
	private static String CONFIG_PROPERTIES_FILE_PATH = System.getProperty("user.dir") + File.separator + "src/test/java/resources/config/config.properties";
	
	public static Properties loadAllFiles() {
		LinkedList<String> files = new LinkedList<>();
		files.add(CONFIG_PROPERTIES_FILE_PATH);
//        files.add(GlobalConstants.DATA_PROPERTIES_FILE_PATH);
//        files.add(GlobalConstants.CRM_LOCATORS_PROPERTIES_FILE_PATH);
		System.out.println("*****************************");
		try {
			properties = new Properties();

			for (String f : files) {
				Properties tempProp = new Properties();
				linkFile = SystemHelpers.getCurrentDir() + f;
				file = new FileInputStream(linkFile);
				tempProp.load(file);
				properties.putAll(tempProp);
			}
			file.close();
//            LogUtils.info("Loaded all properties files.");
			// LogUtils.info(properties);
			return properties;
		} catch (IOException e) {
//            LogUtils.info("Warning !! Can not Load All File.");
			return new Properties();
		}
	}

	public static Properties getProperties() {
		return properties;
	}

	public static void setFile(String relPropertiesFilePath) {
		properties = new Properties();
		try {
			linkFile = SystemHelpers.getCurrentDir() + relPropertiesFilePath;
			file = new FileInputStream(linkFile);
			properties.load(file);
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setDefaultFile() {
		properties = new Properties();
		try {
			linkFile = CONFIG_PROPERTIES_FILE_PATH;
			file = new FileInputStream(linkFile);
			properties.load(file);
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getValue(String key) {
		String keyValue = null;
		try {
			if (file == null && properties == null) {
				properties = new Properties();
				linkFile = CONFIG_PROPERTIES_FILE_PATH;
				file = new FileInputStream(linkFile);
				properties.load(file);
				file.close();
			}
			// Get value from file
			keyValue = properties.getProperty(key);
			return keyValue;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return keyValue;
		}
	}

	public static void setValue(String key, String keyValue) {
		try {
			if (file == null) {
				properties = new Properties();
				file = new FileInputStream(CONFIG_PROPERTIES_FILE_PATH);
				properties.load(file);
				file.close();
				out = new FileOutputStream(CONFIG_PROPERTIES_FILE_PATH);
			}
			// Write to the same Prop file as the extracted file
			out = new FileOutputStream(linkFile);
			System.out.println(linkFile);
			properties.setProperty(key, keyValue);
			properties.store(out, "Set value to properties file success.");
			out.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}