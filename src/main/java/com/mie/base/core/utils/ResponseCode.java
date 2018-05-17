package com.mie.base.core.utils;

/**
 * 响应码：0表示成功，其他表示错误或失败
 *
 */
public enum ResponseCode {

//	Success("0", "i18n_common_alert_successfulOperation", "操作成功"), 
//	Unknown_Error("-1", "i18n_common_alert_systemError","未知错误"), 
//	Fail("1001", "i18n_label_operationfailed", "操作失败"), 
//	Error("1002", "i18n_label_operationfailed", "操作不当"), 
//	Empty("2001", "i18n_js_can_not_be_empty", "参数为空"), 
//	Format("2002", "i18n_common_request_error", "格式错误"), 
//	No_Exist("3001", "i18n_alert_norelatedinfo", "记录不存在或已被删除"), 
//	No_Operation_Permissions("3002", "i18n_account_alert_noPermissionOperation", "没有操作权限");
	
	Success("200", "base_success", "操作成功"), 
	ERROR("500", "base_error", "系统内部异常"), 
	Fail("400", "base_fail", "操作失败"), 
	Param_Error("1401", "base_param_error", "参数异常"),
	Format_Error("1401","base_format_error", "格式错误"),
	METHOD_NO_SUPPORT("1403","base_method_no_support", "不支持当前请求方法"),
	No_Exist("1404", "base_record_no_exist", "记录不存在或已被删除"),
	
	Chinese_Cannot_Be_Null("1405","chinese_cannot_be_null","中文不能为空"),
	
	Account_Permission_denied("1406", "base_permission_denied", "没有操作权限"),
	Account_No_Login("1407", "base_account_no_login", "没有登录,或登录已过期"),
	
	DATABASE_LENGTH_ERROR("1401","database_length_error","输入的参数长度超标"),
	DATABASE_PARSE_ERROR("1401","database_parse_error","输入的参数类型或格式有误"),
	
	
	Account_Create_Fail("1410","base_account_cre_fail","创建账号失败"),
	Account_Expired("1410", "base_account_expired", "帐号已过期"),
	Account_Disabled("1410", "base_account_disabled", "帐号已禁用"),
	Account_Locked("1410", "base_account_locked", "帐号已锁定"),
	The_Same_Account("1410","base_the_same_account","已经存在相同的登录账号"),
	Accouont_Password_Expired("1410", "base_password_expired", "密码过期"),
	Account_Password_Worng("1410", "base_account_password_worng", "用户名或密码错误"),
	Account_Username_Not_Found("1410", "base_account_username_not_found", "找不到该帐号"),
	Account_Sessioin_Expired("1410", "base_account_session_expired", "session会话异常"),
	Account_Captcha_Not_Found("1410","base_account_captcha_not_found","验证码异常"),
	Account_Captcha_Worng("1410","base_account_captcha_worng","验证码有误"),
	User_Not_Found("1410","user_not_found","找不到该用户，无法操作"),
	Can_Not_Be_Null("1401","base_canot_be_null","不能为空"),
	Is_Exists("1402","base_is_exists","已存在,不可重复"),
	Admin_Not_Allow_Oper("1402","admin_not_allow_oper","管理员,不允许操作"),
	Id_Is_Blank("1401","id_is_blank","id为空，操作失败"),
	Unable_System("1401","unable_system","无法匹配系统"),
	Illegal_Opertion("1401","base_illegal_opertion","不合法操作"),
	Donot_Exists("1404","do_not_exists","不存在,无法操作"),
	Data_Error("1401","base_data_error","数据异常"),
	Unable_To_Parse("1401","unable_to_parse","参数格式,无法解析"),
	Query_Condition_Cannot_Be_Empty("1401","query_condition_cannot_be_empty","查询条件不能为空"),
	Parameter_Incomplete("1401","parameter_incomplete","参数不完整"),
	Must_Be_Unique("1401","must_be_unique","必须唯一"),
	Unrecognized("400","unrecognized","无法识别"),
	IsNull("1401","isNull","为空,无法操作"),
	Has_Be_Confirm("1401","has_be_confirm","已经确认,无法再修改"),
	Already_In_Use("1401","already_in_use","已被使用"),
	Achieve_Fail("1401","achieve_fail","获取失败");
	
	private String httpCode;
	private String key;
	private String msg;

	private ResponseCode(String httpCode, String key, String msg) {
		this.httpCode = httpCode;
		this.key = key;
		this.msg = msg;
	}

	
	public String getCode() {
		return httpCode;
	}

	public String getKey() {
		return key;
	}

	public String getMsg() {
		return msg;
	}

}
