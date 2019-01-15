package com.zhy.composite;

import java.util.NoSuchElementException;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesUtil {

	private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

	private static boolean initFlag = false;

	public synchronized void addConfiguration(String path) {
		initFlag = true;
		try {
			PropertiesConfiguration configuration = new PropertiesConfiguration(path);
			configuration.setEncoding("UTF-8");
			CompositeConfig.addConfiguration(configuration, path);
		} catch (Exception e) {
			logger.error("加载配置文件: " + path + " 出错.");
		}
	}

	public static boolean getValueBoolean(String key) {
		try {
			if (!initFlag) {
				logger.error("没有进行初始化");
			}
			return CompositeConfig.getBoolean(key);
		} catch (NoSuchElementException e) {
			// 元素不存在返回false
			return false;
		}
	}

	public static long getValueLong(String key) {
		if (!initFlag) {
			logger.error("没有进行初始化");
		}
		return CompositeConfig.getLong(key);
	}

	public static int getValueInt(String key) {
		if (!initFlag) {
			logger.error("没有进行初始化");
		}
		return CompositeConfig.getInt(key, 0);
	}

	public static int getValueInt(String key, int deaulftvalue) {
		if (!initFlag) {
			logger.error("没有进行初始化");
		}
		return CompositeConfig.getInt(key, deaulftvalue);
	}

	public static String getValueString(String key) {
		if (!initFlag) {
			logger.error("没有进行初始化");
		}
		return CompositeConfig.getString(key);
	}

	public static String getValueString(String key, String defaultValue) {
		String value = getValueString(key);
		if (null == value || "".equals(value)) {
			value = defaultValue;
		}
		return value;
	}

}
