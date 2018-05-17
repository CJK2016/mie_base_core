package com.mie.base.core.exception;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CommonExceptionSerializer  extends JsonSerializer<CommonException> {

	@Override
	public void serialize(CommonException arg0, JsonGenerator arg1, SerializerProvider arg2)
			throws IOException, JsonProcessingException {
		
		Map<String, String > exceptionMap = new HashMap<>();
	   exceptionMap.put("i18nCode", arg0.getI18nCode());
	   exceptionMap.put("message", arg0.getMessage());
	   exceptionMap.put("localizedMessage", arg0.getLocalizedMessage());
	   exceptionMap.put("data", arg0.getData().toString());
	   
	   ObjectMapper m = new ObjectMapper();
	   arg1.writeString(m.writeValueAsString(exceptionMap));
		
	}

}
