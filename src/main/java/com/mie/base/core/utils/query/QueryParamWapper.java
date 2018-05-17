package com.mie.base.core.utils.query;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * 查询条件的包装类
 *
 */
@ApiModel("查询参数统一包装,分隔符_$_, 查询条件包括: = (eq),!= (neq), > (gt), >= (gte), <(lt), <=(lte), like, notLike, isNotNull, isNull, in")
@JsonDeserialize(using=QueryParamWapperDeserializer.class)
public class QueryParamWapper implements Serializable {
	
	@ApiModelProperty(hidden=true)
	private String orderBy;

	@ApiModelProperty(hidden=true)
	private List<QueryParam> queryParams;
	
	private static final long serialVersionUID = 1L;

	public List<QueryParam> getQueryParams() {
		return queryParams;
	}

	public void setQueryParams(List<QueryParam> queryParams) {
		this.queryParams = queryParams;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

}
