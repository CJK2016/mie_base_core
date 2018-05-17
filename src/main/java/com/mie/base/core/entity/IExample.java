package com.mie.base.core.entity;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;

public interface IExample<T extends Serializable> {
	
	public PageView<T> getPageView() ;

	public void setPageView(PageView<T> pageView) ;
	
	public String toJson() throws JsonProcessingException;

	public String toMd5() throws NoSuchAlgorithmException, IOException;
}

