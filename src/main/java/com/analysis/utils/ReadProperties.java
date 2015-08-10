package com.analysis.utils;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class ReadProperties {
	private static ResourceBundle bundle = PropertyResourceBundle.getBundle("datasource");

	public static String getPassword() {
		return bundle.getString("password");
	}

	public static String getUrl() {
		return bundle.getString("url");
	}

	public static String getUsername() {
		return bundle.getString("username");
	}

	/**
	 * @功能：
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(bundle.getString("url"));
	}
}
