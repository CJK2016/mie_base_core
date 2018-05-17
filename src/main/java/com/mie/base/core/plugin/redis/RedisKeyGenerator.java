package com.mie.base.core.plugin.redis;

import com.mie.base.utils.encryption.Md5Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.interceptor.KeyGenerator;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Redis键生成器
 */
public class RedisKeyGenerator implements KeyGenerator {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Object generate(Object target, Method method, Object... params) {

    	String redisKeyStr = null;
    	logger.trace("start generate redis key");
        try {
        	RedisKey redisKey =new RedisKey();
        	//类名称
        	redisKey.setClassName(target.getClass().getName());
        	//方法名称
        	redisKey.setMethodName(method.getName());
        	//方法签名
			redisKey.setParams(this.getParamsMap(method, params));
			//方法的参数
			redisKeyStr = Md5Utils.md5Object(redisKey);
			
		} catch (Exception e) {
			logger.error("生成redis-key失败", e);
			throw new RuntimeException(e);
		}
        logger.trace("finish generate redis key : "+redisKeyStr);
        return redisKeyStr;
    }
    

    /**
     * 拼接key值实现
     * @param method
     * @param params
     * @return
     * @throws NoSuchAlgorithmException
     * @throws JsonProcessingException
     * @throws UnsupportedEncodingException
     */
   /*private String getKey(Object target, Method method, Object... params) throws NoSuchAlgorithmException, UnsupportedEncodingException, JsonProcessingException {

        // @Cacheable(value = "company", key = "'CompanyService_' + #root.methodName + '_' + #companyId +'_'+ T(com.els.base.utils.encryption.Md5Utils).md5Object(#companyExample)")
       StringBuffer key=new StringBuffer();
       key.append(target.getClass()).append("_");
       key.append(method.getName()).append("_");
       key.append(this.getParamsMap(params));
       return  key.toString();
   }*/
   
   private List<Map<String, Object>> getParamsMap(Method method, Object... params) throws NoSuchAlgorithmException, UnsupportedEncodingException, JsonProcessingException{
	   if(params == null || params.length == 0){
		   return null;
	   }
	   
	   Class<?>[] paramTypes = method.getParameterTypes();
	   
	   List<Map<String, Object>> paramsMap = new ArrayList<>();
	   for (int i=0; i<params.length; i++) {
           Map<String, Object> map = new HashMap<>();
           map.put(paramTypes[i].getName(),  Md5Utils.md5Object(params[i]));
           paramsMap.add(map);
       }
	   return paramsMap;
   }


}