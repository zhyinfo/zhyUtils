package com.zhy.utils.http;

/**
 * @ClassName   : ContentTypeEnum   
 * @Description : contenttype类型
 * @author : zhy
 * @date   : 2018年8月8日 上午8:51:32 
 */
public enum ContentTypeEnum {
	
	FORM("application/x-www-form-urlencoded;charset=utf-8"),
	JSON("application/json;charset=utf-8");
	
	private String contentType;
	
	private ContentTypeEnum(String contentType) {
		this.contentType = contentType;
	}

	public String getContentType() {
		return contentType;
	}
	
}
