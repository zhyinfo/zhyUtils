package com.zhy.common;


/**
 * 
 * @Title: BaseResult
 * @Package com.space.common
 * @Description: 返回结果类
 * @author gaven
 * @date 2018年7月26日 下午2:16:21
 */
public class BaseResult<T> {
	/**
	 * 是否成功
	 */
	private boolean success;
	/**
	 * 错误码 
	 */
 	private Integer errorCode;
	/**
	 * 错误信息  前端可以根据异常信息做更友好提示提示
	 */
	private String errorMessage;
	/**
	 * 返回数据
	 */
	private T data;

	public BaseResult() {
		super();
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public BaseResult(T data) {
		super();
		this.success = true;
		this.data = data;
	}

	public BaseResult(Integer errorCode, String errorMassage, T data) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMassage;
		this.data = data;
		this.success = false;
	}
	
	public BaseResult(ErrorCode errorCode) {
		super();
		this.errorCode = errorCode.getCode();
		this.errorMessage = errorCode.getDesc();
		this.success = false;
	}
	
	public BaseResult(Integer errorCode, String errorMassage) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMassage;
		this.success = false;
	}
	
	public BaseResult(BaseException ex) {
		super();
		this.errorCode = ex.getErrorCode();
		this.errorMessage = ex.getErrorMessage();
		this.success = false;
	}
	
	public BaseResult(BaseException ex,T data) {
		super();
		this.errorCode = ex.getErrorCode();
		this.errorMessage = ex.getErrorMessage();
		this.data = data;
		this.success = false;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMassage) {
		this.errorMessage = errorMassage;
	}

}
