package com.zhy.composite;

import java.util.List;
import java.util.NoSuchElementException;

import org.apache.commons.configuration.XMLConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlConfigUtil {

	private static Logger log = LoggerFactory.getLogger(XmlConfigUtil.class);

	private static boolean initFlag = false;

	public XmlConfigUtil() {

	}

	public synchronized void addConfiguration(String path) {
		initFlag = true;
		try {
			XMLConfiguration configuration = new XMLConfiguration(path);
			configuration.setEncoding("UTF-8");
			CompositeConfig.addConfiguration(configuration, path);
		} catch (Exception e) {
			log.error("加载配置文件: " + path + " 出错.");
		}
	}

	public static String[] getValueArray(String key) {
		if (!initFlag) {
			log.error("没有初始化");
		}
		return CompositeConfig.getStringArray(key);
	}

	public static int getValueInt(String key) {
		if (!initFlag) {
			log.error("没有初始化");
		}
		return CompositeConfig.getInt(key, 0);
	}

	public static int getValueInt(String key, int defaultValue) {
		if (!initFlag) {
			log.error("没有初始化");
		}
		return CompositeConfig.getInt(key, defaultValue);
	}

	public static String getValueString(String key) {
		if (!initFlag) {
			log.error("没有初始化");
		}
		return CompositeConfig.getString(key);
	}

	public static String getValueString(String key, String defaultValue) {
		if (!initFlag) {
			log.error("没有初始化");
		}
		String value = CompositeConfig.getString(key);
		return null == value ? defaultValue : value;
	}

	public static boolean getValueBoolean(String key) {
		if (!initFlag) {
			log.error("没有初始化");
		}
		try {
			return CompositeConfig.getBoolean(key);
		} catch (NoSuchElementException e) {
			// 元素不存在返回false
			return false;

		}

	}

	@SuppressWarnings("unchecked")
	public static List getValueList(String key) {
		if (!initFlag) {
			log.error("没有初始化");
		}
		return CompositeConfig.getList(key);
	}
}
