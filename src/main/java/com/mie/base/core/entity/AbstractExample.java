package com.mie.base.core.entity;

import com.mie.base.utils.encryption.Md5Utils;
import com.mie.base.utils.json.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;

public abstract class AbstractExample<T extends Serializable> implements IExample<T> ,  Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Serializable filterParams;
	
	public Serializable getFilterParams() {
		return filterParams;
	}

	public void setFilterParams(Serializable filterParams) {
		this.filterParams = filterParams;
	}

	@Override
	public String toJson() throws JsonProcessingException {
        return JsonUtils.writeValueAsString(this);
	}

	@Override
	public String toMd5() throws NoSuchAlgorithmException, IOException {
		return Md5Utils.md5Encode(this.toJson());
	}
	
}
