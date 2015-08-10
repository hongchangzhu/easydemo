package com.analysis.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class Config {
	private static ResourceBundle bundle;
	private static Map<String, ResourceBundle> configMap = new HashMap<String, ResourceBundle>();

	public static String getValue(String filename, String key) {
		bundle = configMap.get(filename);
		if (bundle == null) {
			bundle = PropertyResourceBundle.getBundle(filename);
			configMap.put(filename, bundle);
		}
		return bundle.getString(key);
	}
}
