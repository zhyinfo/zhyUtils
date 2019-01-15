package com.zhy.common;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

/**
 * @ClassName   : CommonUtils   
 * @Description : TODO(这里用一句话描述这个类的作用)   
 * @author : zhy
 * @date   : 2018年8月8日 下午2:29:50 
 */
public class CommonUtils {
	
	private CommonUtils(){}
	
	private static final String GET = "get";
	private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * @Title  : checkAndBeanToMap   
	 * @Description :  1: 校验参数是否为空; 2: 获取参与加密参数
	 * @param obj
	 * @author : zhy
	 * @date   : 2018年8月9日 上午10:31:53 
	 */
	public static BaseResult<? extends Object> checkAndBeanToMap(Object obj) throws Exception{
		if(null == obj){
			return null;
		}
		Map<String, String> singParams = new HashMap<String, String>();
		List<Class<? extends Object>> clzs = new ArrayList<Class<? extends Object>>();
		getClass(obj.getClass(),clzs);
		for (Class<? extends Object> clz : clzs) {
			Field[] fields = clz.getDeclaredFields();
			for (Field field : fields) {
				String k = field.getName();
				Method method = clz.getDeclaredMethod(GET+k.substring(0,1).toUpperCase()+k.substring(1));
				Object v = method.invoke(obj);
				Annotation[] annos = field.getAnnotations();
				for (Annotation anno : annos) {
					if(anno.annotationType().equals(Column.class)){
						Column c = (Column)anno;
//						if(!c.isNil() && null == v){
						if(!c.isNil() && StringUtils.isEmpty(v)){
							return new BaseResult<Object>(new BaseException(ErrorCode.ERROR_PARAM_BLANK));
//							return new BaseResult<Map<String, String>>(10003, "缺少必要参数", null);
						}
						if(c.isSign() && !StringUtils.isEmpty(v)){
							singParams.put(k, v.toString());
						}
					}
				}	
			}
		}
		return new BaseResult<Map<String, String>>(singParams);
	}
	
	private static List<Class<? extends Object>> getClass(Class<? extends Object> clz,List<Class<? extends Object>> clzs){
		if("java.lang.Object".equals(clz.getName())){
			return clzs;
		}
		clzs.add(clz);
		return getClass(clz.getSuperclass(),clzs);
	}
	/**
	 * @Title  : concatParams   
	 * @Description : 参数拼接 ; 格式：A=1&B=2&C=3... 
	 * @param params
	 * @param flag :是否对参数排序
	 * @return :params        
	 * @author : zhy
	 * @throws Exception 
	 * @date   : 2018年8月8日 上午11:26:35 
	 */
	public static String concatParams(Map<String, String> params,boolean flag) throws Exception{
		StringBuilder builder = new StringBuilder();
		List<String> keys = new ArrayList<String>(params.keySet());
		if(flag){
			Collections.sort(keys);
		}
		for (String key : keys) {
			builder.append(key).append("=").append(params.get(key) != null ? URLEncoder.encode(params.get(key),"utf-8") : "").append("&");
		}
		return builder.deleteCharAt(builder.length()-1).toString();
	}

	/**
	 * 时间字符串转换成时间
	 * @param dateStr
	 * @return
	 */
	public static Date strToDate(String dateStr){
		Date parse = null;
		try {
			parse = formatter.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return parse;
	}

	/**
	 * 实体对象转成Map
	 * @param obj 实体对象
	 * @return
	 */
	public static Map<String, String> object2Map(Object obj) {
		Map<String, String> map = new HashMap<>();
		if (obj == null) {
			return map;
		}
		Class clazz = obj.getClass();
		Field[] fields = clazz.getDeclaredFields();//获取所有属性
		try {
			for (Field field : fields) {
				field.setAccessible(true);//获取权限
				map.put(field.getName(), field.get(obj)+"");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
}
