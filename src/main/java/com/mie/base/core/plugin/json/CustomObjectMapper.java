package com.mie.base.core.plugin.json;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomObjectMapper extends ObjectMapper {

	private static final long serialVersionUID = -6098428888177199153L;

	public CustomObjectMapper() {
		super();
		this.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
		this.configure(Feature.ALLOW_SINGLE_QUOTES, true);
	}

	
	

}
