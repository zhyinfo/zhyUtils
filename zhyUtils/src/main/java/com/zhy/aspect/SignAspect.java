//package com.zhy.aspect;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//import java.lang.reflect.Parameter;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//
//import com.ecar.yema.common.annotation.Sign;
//import com.ecar.yema.common.config.tsp.TspConfig;
//import com.ecar.yema.common.constant.Constant;
//import com.ecar.yema.common.exception.BusinessException;
//import com.ecar.yema.common.utils.AuthSigUtil;
//
//import ecar.componet.errorcode.ErrorCode;
//
///**
// * @ClassName   : SignAspect   
// * @Description : AOP, 标注有@sign注解的方法将进行签名校验
// * @author : zhy
// * @date   : 2018年10月9日 上午11:51:47 
// */
//@Order(2)
//@Aspect
//@Component
//public class SignAspect {
//	
//	private Logger logger = LoggerFactory.getLogger(getClass());
//	
////	@Autowired
////	private TspConfig config;
//	
//	@Pointcut("execution(public * com.ecar.yema.*.rest..*Rest.*(..)) && (@annotation(com.ecar.yema.common.annotation.Sign))")
//    public void sign(){}
//	
//	@Before("sign()")
//	public void doBefore(JoinPoint joinPoint) throws Throwable{
//		Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
//		if(method.getAnnotation(Sign.class).isSign()){
//			Map<String, String> signParams = new HashMap<String, String>();
//			Object[] args = joinPoint.getArgs();
//			Parameter[] parameters = method.getParameters();
//			for (int i = 0; i < parameters.length; i++) {
//				Parameter parameter = parameters[i];
//				Class<?> clz = parameter.getType();
//				if(isPrimite(clz)){
//					if(parameter.isAnnotationPresent(Sign.class)){
//						if(args[i] == null){
//							throw new BusinessException(ErrorCode.ERROR_PARAM_BLANK);
//						}
//						signParams.put(parameter.getName(), String.valueOf(args[i]));
//					}
//					continue;
//				}
//				//没有标注@Sign注解，或者是HttpServletRequest、HttpServletResponse、HttpSession时，都不做处理
//	            if (clz.isAssignableFrom(HttpServletRequest.class) || 
//					clz.isAssignableFrom(HttpSession.class) ||
//					clz.isAssignableFrom(HttpServletResponse.class)) {
//	                continue;
//	            }
//	            //获取类型所对应的参数对象，实际项目中Controller中的接口不会传两个相同的自定义类型的参数，所以此处直接使用findFirst()
//	            Object arg = Arrays.stream(args).filter(ar -> clz.isAssignableFrom(ar.getClass())).findFirst().get();
//	            //得到参数的所有成员变量
//	            List<Field> fieldList = new ArrayList<Field>();
//	            getFields(clz,fieldList);
//	            for (Field field : fieldList) {
//					field.setAccessible(true);
//					Sign sign = field.getAnnotation(Sign.class); //com.ecar.yema.base.BaseVO.sign
//					if(null != sign && (sign.isSign() || Constant.SIGN_FIELD.equals(field.getName()))){
//						Object object = field.get(arg);
//						if(null == object){
//							throw new BusinessException(ErrorCode.ERROR_PARAM_BLANK);
//						}
//						signParams.put(field.getName(), String.valueOf(object));
//					}
//				}
//			}
//			
//			logger.info("=->> "+joinPoint.getSignature().toShortString() +" sign params -> {}",signParams);
//			String sign = signParams.remove(Constant.SIGN_FIELD);
//			if(StringUtils.isEmpty(sign)){
//				throw new BusinessException(ErrorCode.ERROR_PARAM_BLANK);
//			}
//			AuthSigUtil.sign(signParams, sign);
//		}
//	}
//	
//	private void getFields(Class<?> clazz,List<Field> fieldList){
//		if(clazz == null){
//			return;
//		}
//		fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
//		getFields(clazz.getSuperclass(), fieldList);
//	}
//	
//    /**
//     * @Title  : isPrimite   
//     * @Description : 判断是否为基本类型：包括String  
//     * @param  : clazz
//     * @return : true：是; false：不是      
//     * @author : zhy
//     * @date   : 2018年10月9日 下午2:26:58 
//     */
//    private boolean isPrimite(Class<?> clazz){
//        return clazz.isPrimitive() || clazz == String.class;
//    }
//}
