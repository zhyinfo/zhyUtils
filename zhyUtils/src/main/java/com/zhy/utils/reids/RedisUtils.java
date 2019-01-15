package com.zhy.utils.reids;

import java.util.Collections;

import redis.clients.jedis.Jedis;

/**
 * @ClassName   : RedisUtils   
 * @Description : redis工具类
 * @author : zhy
 * @date   : 2018年11月16日 下午4:59:03 
 */
public class RedisUtils {
	
	private static final String LOCK_SUCCESS = "OK";
	private static final Long RELEASE_SUCCESS = 1L;
	private static final String SET_IF_NOT_EXIST = "NX";
	private static final String SET_WITH_EXPIRE_TIME = "PX";
	
	/**
	 * @Title  : tryGetDistributedLock   
	 * @Description : 尝试获取分布式锁   
	 * @param jedis ：jedis客户端
	 * @param lockKey ：锁
	 * @param requestId ：请求标识，唯一
	 * @param expireTime：失效时间
	 * @author : zhy
	 * @date   : 2018年11月16日 下午5:10:23 
	 */
	public static boolean tryGetDistributedLock(Jedis jedis,String lockKey,String requestId,int expireTime){
		
		String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
		if(LOCK_SUCCESS.equals(result)){
			return true;
		}
		return false;
	}
	
	/**
	 * @Title  : releaseDistributedLock   
	 * @Description : 分布式锁释放   
	 * @param jedis ：jedis客户端
	 * @param lockKey ：锁
	 * @param requestId：请求标识
	 * @return : 锁是否释放成功        
	 * @author : zhy
	 * @date   : 2018年11月16日 下午5:20:14 
	 */
	public static boolean releaseDistributedLock(Jedis jedis,String lockKey,String requestId){
		
		String script = "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
		Object result = jedis.eval(script,Collections.singletonList(lockKey),Collections.singletonList(requestId));
		
		if(RELEASE_SUCCESS.equals(result)){
			return true;
		}
		return false;
	}

}
