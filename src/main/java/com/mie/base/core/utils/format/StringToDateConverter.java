package com.mie.base.core.utils.format;

import com.mie.base.core.exception.CommonException;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.convert.converter.Converter;

import java.util.Date;

/**
 *
 *
 */
public class StringToDateConverter implements Converter<String, Date>{
	
	@Override
	public Date convert(String source) {
		if (StringUtils.isBlank(source)){
			return null;
		}
		
		if (StringUtils.isBlank(source) || !StringUtils.isNumeric(source)) {
			throw new CommonException("参数格式,无法解析", "unable_to_parse");
		}
		
		return new Date(Long.valueOf(source));
	}


}
