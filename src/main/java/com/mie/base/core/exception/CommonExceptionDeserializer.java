package com.mie.base.core.exception;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class CommonExceptionDeserializer extends JsonDeserializer<CommonException> {

	@Override
	public CommonException deserialize(JsonParser arg0, DeserializationContext arg1)
			throws IOException, JsonProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

}
