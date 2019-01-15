package com.zhy.common;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName   : Column   
 * @Description : TODO(这里用一句话描述这个类的作用)   
 * @author : zhy
 * @date   : 2018年8月10日 上午11:58:23 
 */
@Target({FIELD,METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
	/*
	 * @Description  : isNil   校验参数是否允许为空，默认 不为空
	 */
	boolean isNil() default false;

	/* 
	 * @Description : isSign  sign签名参数 默认否
	 */
	boolean isSign() default false;

}
