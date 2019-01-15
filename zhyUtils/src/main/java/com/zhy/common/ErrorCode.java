package com.zhy.common;

/**
 * 
 * @Title: ErrorCode
 * @Package com.ecar.telesales.exception
 * @Description: 错误码
 * 
 *               code格式：00 00 00 1-2位数字 工程号 公共异常码10开头 ;3-4位数字 项目号 ;5-6位数字 异常编码
 * @author gaven
 * @date 2018年7月25日 下午3:26:05
 */
public enum ErrorCode {
	// -----------公共异常码------------//
	/**
	 * 执行异常
	 */
	ERROR_UNKNOW(100500, "执行异常"),
	/**
	 * 访问权限受限
	 */
	ERROR_NO_PERMISSION(100010,"无权限访问"),
	/**
	 * 参数为空
	 */
	ERROR_PARAM_BLANK(100020, "必要的参数为空"),
	/**
	 * 参数非法
	 */
	ERROR_PARAM_ILLEGAL(100021, "参数非法"),
	/**
	 * 参数非法
	 */
	ERROR_DB_QUERY(100030, "数据库查询错误"),
	;

	private ErrorCode(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	/**
	 * 错误码 code格式：00 00 00 1-2位数字 工程号 公共异常码10开头 3-4位数字 项目号 5-6位数字 异常 项目
	 */
	private int code;
	/**
	 * 错误描述
	 */
	private String desc;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
