//package com.zhy.aspect;
//
//import org.slf4j.LoggerFactory;
//import org.springframework.validation.BindException;
//import org.springframework.validation.FieldError;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.MissingServletRequestParameterException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import com.ecar.yema.common.constant.Constant;
//import com.ecar.yema.common.constant.ErrorMessageEnum;
//import com.ecar.yema.common.exception.BusinessException;
//
//import ch.qos.logback.classic.Logger;
//import ecar.componet.domain.BaseResult;
//import ecar.componet.errorcode.ErrorCode;
//import ecar.componet.exception.BaseException;
//
///**
// * 
// * @Title: ExceptionHandlerAdvice
// * @Package com.space.common.aspect
// * @Description: TODO类功能描述
// * @author gaven
// * @date 2018年7月26日 下午2:13:06
// */
//@RestControllerAdvice
//@SuppressWarnings("rawtypes")
//public class ExceptionHandlerAdvice {
//	Logger logger = (Logger) LoggerFactory.getLogger(getClass());
//
//	/**
//	 * 处理所有不可知的异常
//	 */
//	@ExceptionHandler(Exception.class)
//	public BaseResult handleException(Exception e) {
//		logger.error("system error!", e);
//		return new BaseResult(ErrorCode.ERROR_UNKNOW.getCode(),ErrorCode.ERROR_UNKNOW.getDesc());
//	}
//
//	/**
//	 * 处理所有业务异常
//	 */
//	@ExceptionHandler(BusinessException.class)
//	public BaseResult handleBusinessException(BusinessException e) {
//		logger.error(e.getErrorMessage(), e);
//		return new BaseResult(e.getErrorCode(),e.getErrorMessage());
//	}
//	
//	@ExceptionHandler(BaseException.class)
//	public BaseResult handleBaseException(BaseException e){
//		logger.error(e.getErrorMessage(), e);
//		return new BaseResult(e.getErrorCode(),e.getErrorMessage());
//	}
//	
//	//**************************************************入参校验********************************************************/
//	/**
//	 * @Title  : handleConstraintViolationException   
//	 * @Description : 数据校验不通过，则Spring boot会抛出BindException异常         
//	 * @author : zhy
//	 * @date   : 2018年10月9日 上午10:48:06 
//	 */
//	@ExceptionHandler(BindException.class)
//	public BaseResult<String> handleConstraintViolationException(BindException e){
//		// e.getFieldError():随机返回一个对象属性的异常信息。如果要一次性返回所有对象属性异常信息，则调用e.getAllErrors()
//		FieldError fieldError = e.getFieldError();
//		String errorMessage = new StringBuilder(fieldError.getField()).append("=").append(fieldError.getRejectedValue())
//							   .append(",").append(fieldError.getDefaultMessage()).toString();
//		logger.error(errorMessage, e);
//		
//		return new BaseResult<String>(ErrorMessageEnum.IS_INVALID_PARM.getErrCode(),errorMessage);
//	}
//	
//	@ExceptionHandler(MethodArgumentNotValidException.class)
//	public BaseResult<String> handleConstraintViolationException(MethodArgumentNotValidException e){
//		FieldError fieldError = e.getBindingResult().getFieldError();
//		String errorMessage = new StringBuilder(fieldError.getField()).append("=").append(fieldError.getRejectedValue())
//				.append(",").append(fieldError.getDefaultMessage()).toString();
//		logger.error(errorMessage, e);
//		
//		return new BaseResult<String>(ErrorMessageEnum.IS_INVALID_PARM.getErrCode(),errorMessage);
//	}
//	
//	
//
//	/**
//	 * @Title  : handleConstraintViolationException   
//	 * @Description : 处理@RequestParam校验异常      
//	 * @author : zhy
//	 * @date   : 2018年10月9日 上午10:48:46 
//	 */
//	@ExceptionHandler(MissingServletRequestParameterException.class)
//    public BaseResult<String> handleConstraintViolationException(MissingServletRequestParameterException e){
//		logger.error(String.format(Constant.PARAM_ERROR_EXPLAIN, e.getParameterType(),e.getParameterName()), e);
//		
//        return new BaseResult<String>(ErrorMessageEnum.IS_INVALID_PARM.getErrCode(),
//        		   String.format(Constant.PARAM_ERROR_EXPLAIN, e.getParameterType(),e.getParameterName()));
//    }
//	//**************************************************入参校验********************************************************/
//
//
//}
