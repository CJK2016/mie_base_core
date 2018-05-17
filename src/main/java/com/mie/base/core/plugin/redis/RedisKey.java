package com.mie.base.core.plugin.redis;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 类，方法，参数
 */
public class RedisKey implements Serializable{

	private static final long serialVersionUID = 1L;

	private  String className;

    private String methodName;

    private List<Map<String,Object>> params;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public List<Map<String, Object>> getParams() {
        return params;
    }

    public void setParams(List<Map<String, Object>> params) {
        this.params = params;
    }
}
