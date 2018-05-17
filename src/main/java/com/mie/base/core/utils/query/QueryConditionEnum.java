package com.mie.base.core.utils.query;

/**
 * 查询条件的枚举
 * @author hzy
 *
 */
public enum QueryConditionEnum {
	
	LIKE("like", "like"),
	NOT_LIKE("notLike", "notLike"),
	IS_NOT_NULL("isNotNull", "isNotNull"),
	IS_NULL("isNull", "isNull"),
	IN("in", "in"),
	EQUAL_TO("=", "equalTo"),
	NOT_EQUAL_TO("!=", "notEqualTo"),
	GREATER_THAN(">", "greaterThan"),
	GREATER_THAN_OR_EQUAL_TO(">=", "greaterThanOrEqualTo"),
	LESS_THAN("<", "lessThan"),
	LESS_THAN_OR_EQUAL_TO("<=", "lessThanOrEqualTo");
	
	private String shortName;
	private String fullName;
	
	private QueryConditionEnum(String shortName, String fullName) {
		this.shortName = shortName;
		this.fullName = fullName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	

}
