package com.mie.base.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mie.base.core.utils.ResponseCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * 响应结果
 * 
 */
@ApiModel(value = "响应信息")
public class ResponseResult<T> implements Serializable {

    private static Logger logger = LoggerFactory.getLogger(ResponseResult.class);

    private static final long serialVersionUID = 6382523290032620956L;

    @ApiModelProperty("响应码，表示请求的状态。200表示成功，其他表示失败")
    private String code; // 响应码：0表示成功，其他表示失败

    @ApiModelProperty("响应描述")
    private String msg; // 响应描述

    @ApiModelProperty("返回数据")
    private T data; // 返回数据

    @JsonIgnore
    @ApiModelProperty("国际化代码")
    private String i18nCode;

    @JsonIgnore
    @ApiModelProperty("国际化输入参数")
    private Object[] i18nArgs;

    public ResponseResult() {
        super();
    }

    public ResponseResult(String code, String msg, T data) {
        super();
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public ResponseResult<T> setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        // 如果没有配置国际化code，就直接返回
        if (StringUtils.isBlank(this.getI18nCode())) {
            return this.msg;
        };
        //TODO 如果配置了国际化code，将进行国际化操作
        return this.msg;
    }

    public ResponseResult<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public ResponseResult<T> setData(T data) {
        this.data = data;
        return this;
    }

    public String getI18nCode() {
        return i18nCode;
    }

    public Object[] getI18nArgs() {
        return i18nArgs;
    }

    public void setI18nArgs(Object[] i18nArgs) {
        this.i18nArgs = i18nArgs;
    }

    public ResponseResult<T> setI18nCode(String i18nCode) {
        this.i18nCode = i18nCode;
        return this;
    }

    public static ResponseResult<String> success() {
        return success("");
    }

    public static <T> ResponseResult<T> success(T data) {
        return new ResponseResult<T>().setCode(ResponseCode.Success.getCode())
                .setI18nCode(ResponseCode.Success.getKey()).setMsg(ResponseCode.Success.getMsg())
                .setData(data);
    }

    public static ResponseResult<String> fail(ResponseCode code) {
        return fail(code, "");
    }

    public static <T> ResponseResult<T> fail(ResponseCode code, T data) {
        return new ResponseResult<T>().setCode(code.getCode()).setI18nCode(code.getKey())
                .setMsg(code.getMsg()).setData(data);
    }

}
