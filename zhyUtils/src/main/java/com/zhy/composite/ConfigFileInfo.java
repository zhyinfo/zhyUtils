package com.zhy.composite;

public class ConfigFileInfo {
	private long fileLastModifyTime;

	private String filePath;

	private String invokClass;

	private int fileType; // 0 properties文件 1XML文件

	public ConfigFileInfo(long fileLastModifyTime, String filePath,String invokClass, int fileType) {
		this.fileLastModifyTime = fileLastModifyTime;
		this.filePath = filePath;
		this.invokClass = invokClass;
		this.fileType = fileType;

	}

	public int getFileType() {
		return fileType;
	}

	public void setFileType(int fileType) {
		this.fileType = fileType;
	}

	public long getFileLastModifyTime() {
		return fileLastModifyTime;
	}

	public void setFileLastModifyTime(long fileLastModifyTime) {
		this.fileLastModifyTime = fileLastModifyTime;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getInvokClass() {
		return invokClass;
	}

	public void setInvokClass(String invokClass) {
		this.invokClass = invokClass;
	}

}
