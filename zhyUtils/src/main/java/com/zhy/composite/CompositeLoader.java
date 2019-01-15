package com.zhy.composite;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.Resource;

/**
 * @ClassName : CompositeLoader
 * @Description : 加载配置文件(.xml/.properties)
 * @author : zhy
 * @date : 2018年8月15日 上午9:18:15
 */
public class CompositeLoader implements ApplicationListener<ContextRefreshedEvent>, Runnable {

	private Logger logger = LoggerFactory.getLogger(CompositeLoader.class);
	private static boolean LOAD_FLAG = true;

	private XmlConfigUtil xmlUtil = new XmlConfigUtil();
	private PropertiesUtil putil = new PropertiesUtil();
	private List<String> nameList = new ArrayList<String>();
	private Map<String, ConfigFileInfo> fileMap = new HashMap<String, ConfigFileInfo>();
	
	@Value("classpath:config/dyna/**/*.*")
	private Resource[] locations;
	
	@Value("60")
	private int monitorTime = 30;
	
	/**
	 * 初使化配置文件
	 * 
	 * @param event
	 * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
	 **/
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (LOAD_FLAG) {
			LOAD_FLAG = false;
			//step 1 握手配置文件
			touchFiles();
			//step 2 开始检查配置文件的变化
			new Thread(this).start();
		}
	}

	/**
	 * 握手配置文件
	 */
	private void touchFiles() {
		if (this.locations != null) {
			for (Resource location : this.locations) {
				try {
					String path = location.getURL().getPath();
					String fileName = location.getFilename();
					File file = new File(path);

					if (!nameList.contains(fileName)) {
						nameList.add(fileName);
						long nowModifyTime = file.lastModified();
						int fileType = 0;
						if (null != path && path.endsWith("xml")) {
							xmlUtil.addConfiguration(path);
							fileType = 1;
						} else {
							putil.addConfiguration(path);
						}
						ConfigFileInfo fileInfo = new ConfigFileInfo(nowModifyTime, path, null, fileType);
						fileMap.put(fileName, fileInfo);
					} else {
						ConfigFileInfo fileInfo = fileMap.get(fileName);
						long lastModifyTime = fileInfo.getFileLastModifyTime();
						long nowModifyTime = file.lastModified();
						if (nowModifyTime > lastModifyTime) {
							logger.info("[===================>> 有更新的配置文件:{} <<===================]",fileName);
							fileInfo.setFileLastModifyTime(nowModifyTime);
							fileMap.put(fileName, fileInfo);
							if (null != path && path.endsWith("xml")) {
								xmlUtil.addConfiguration(path);
							} else {
								putil.addConfiguration(path);
							}
						}
					}
				} catch (Exception e) {
					logger.error("Could not load xml from:" + location, e);
				}
			}
		}

	}

	/**
	 * 用于检查文件的更新
	 * 
	 * @see java.lang.Runnable#run()
	 **/
	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(this.monitorTime * 1000);
				touchFiles();
			} catch (Exception e) {
				logger.error("monitor composite files error:", e);
			}
		}
	}

	// ----------------------------------------------------------
	public Resource[] getLocations() {
		return locations;
	}

	public void setLocations(Resource[] locations) {
		this.locations = locations;
	}

	public int getMonitorTime() {
		return monitorTime;
	}

	public void setMonitorTime(int monitorTime) {
		this.monitorTime = monitorTime;
	}

}
