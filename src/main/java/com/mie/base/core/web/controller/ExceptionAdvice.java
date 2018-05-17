package com.mie.base.core.web.controller;

import com.mie.base.core.entity.ResponseResult;
import com.mie.base.core.exception.CommonException;
import com.mie.base.core.utils.ResponseCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class ExceptionAdvice {

	private static Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);


	//Caused by: java.sql.SQLException: ORA-12899: 列 "SYSTEM"."T_BASE_DELIVERY_ORDER"."EXPRESS_NUMBER" 的值太大 (实际值: 506, 最大值: 255)
	@ExceptionHandler(UncategorizedSQLException.class)
	@ResponseBody
	public ResponseResult<String> handleSQLException(UncategorizedSQLException e) {
		logger.error("数据超长字段，请核对字段是否超出限制", e);
		return ResponseResult.fail(ResponseCode.DATABASE_LENGTH_ERROR);
	}


	/**
	 * 400 - Bad Request
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseBody
	public ResponseResult<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
		logger.error("参数解析失败", e);
		return ResponseResult.fail(ResponseCode.DATABASE_PARSE_ERROR);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	@ResponseBody
	public ResponseResult<String> handleDataIntegrityViolationException(DataIntegrityViolationException e){
	    logger.error("数据表字段类型设置的长度大小异常",e);
	    return ResponseResult.fail(ResponseCode.DATABASE_LENGTH_ERROR);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseBody
	public ResponseResult<String> handleIllegalArgumentException(IllegalArgumentException e) {
		logger.warn("Token异常:" + e.getMessage());
		ResponseResult<String> responseResult = ResponseResult.fail(ResponseCode.Fail); 
		responseResult.setMsg(e.getMessage());
		return responseResult;
	}
	
	/**
	 * 405 - Method Not Allowed
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	@ResponseBody
	public ResponseResult<String> handleHttpRequestMethodNotSupportedException(
			HttpRequestMethodNotSupportedException e) {
		logger.error("不支持当前请求方法", e);
		return ResponseResult.fail(ResponseCode.METHOD_NO_SUPPORT);
	}

	/** 
     * 415 - Unsupported Media Type 
     */  
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseBody
    public ResponseResult<String> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {  
        logger.error("不支持当前媒体类型", e);  
       return ResponseResult.fail(ResponseCode.Format_Error);
    }
    
    @ExceptionHandler(CommonException.class)
	@ResponseBody
	public ResponseResult<String> handleCmException(CommonException e) {
		logger.debug("业务异常:" + e.getMessage());
		ResponseResult<String> responseResult = ResponseResult.fail(ResponseCode.Fail); 
		responseResult.setMsg(e.getMessage());
		responseResult.setI18nCode(e.getI18nCode());
		responseResult.setI18nArgs(e.getI18nArgs());
		return responseResult;
	}

	/**
	 * 500 - Internal Server Error
	 */
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ResponseResult<String> handleException(Exception e) {
		CommonException newException = CommonException.matchCommonException(e.getMessage());
		if(newException != null) return handleCmException(newException);
		
		logger.error("系统异常", e);
		
		ResponseResult<String> responseResult = ResponseResult.fail(ResponseCode.ERROR); 
		responseResult.setMsg("系统内部异常");
		responseResult.setI18nCode(ResponseCode.ERROR.getKey());
		return responseResult;
	}
	
	/*public static void main(String[] args) throws IOException {
		
		try {
			CommonException realE = new CommonException("ceshi");
			throw new CommonException(realE.toString());
		} catch (Exception e) {
			e.printStackTrace();
			CommonException nextE = CommonException.matchCommonException(e.getMessage());
			System.out.println(nextE);
			System.out.println(nextE.getMessage());
			
		}
		
	}*/

}
