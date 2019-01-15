package com.zhy.common;
public class BaseException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2272751765022471331L;

	private static final String UNKNOW_ERROR = "500";

	private Integer errorCode; // 错误码

	private String errorMessage; // 错误描述

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public BaseException(Throwable cause) {
		super(cause);
	}

	public BaseException(Integer errorCode, Throwable cause) {
		super(cause);
		this.errorCode = errorCode;
		this.errorMessage = cause.getMessage();
	}

	public BaseException(Integer errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	
	/**
	 *  异常 根据errorCode 自定义异常描述
	 * @param errorCode
	 * @param errorMessage
	 */
	public BaseException(ErrorCode errorCode, String errorMessage) {
		this.errorCode = errorCode.getCode();
		this.errorMessage = errorMessage;
	}

	public BaseException(ErrorCode errorCode) {
		this.errorCode = errorCode.getCode();
		this.errorMessage = errorCode.getDesc();
	}

	public BaseException(ErrorCode errorCode, Throwable cause) {
		super(cause);
		this.errorCode = errorCode.getCode();
		this.errorMessage = errorCode.getDesc();
	}

	public static String getErrorCode(String errorCode) {
		if (errorCode == null || "".equals(errorCode)) {
			errorCode = UNKNOW_ERROR;
		}
		return errorCode;
	}

}
