package com.zhy.composite;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;

public class CompositeConfig {
	private static CompositeConfiguration config = new CompositeConfiguration();

	private static Map<String, Configuration> map = new HashMap<String, Configuration>(5);

	public static void addConfiguration(Configuration configuration, String path) {
		if (map.containsKey(path)) {
			Configuration config = map.get(path);
			config.clear();
		}
		config.addConfiguration(configuration);
		map.put(path, configuration);

	}

	public static String[] getStringArray(String key) {
		return config.getStringArray(key);
	}

	public static int getInt(String key, int i) {
		return config.getInt(key, i);
	}

	public static String getString(String key) {
		return config.getString(key);
	}

	public static boolean getBoolean(String key) {
		return config.getBoolean(key);
	}

	@SuppressWarnings("unchecked")
	public static List getList(String key) {
		return config.getList(key);
	}

	public static long getLong(String key) {
		return config.getLong(key);
	}

}
