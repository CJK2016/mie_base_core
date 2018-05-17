package com.mie.base.core.utils;

import com.mie.base.core.exception.CommonException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.Collection;
import java.util.List;

/**
 * 帮助验证参数的断言工具
 *
 */
public class Assert {

    /**
     * 列表不能为空，否则就报错
     * 
     * @param list
     * @param defaultMsg
     */
    @SuppressWarnings("rawtypes")
    public static void isNotEmpty(Collection list, String defaultMsg) {
        if (CollectionUtils.isEmpty(list))
            throw new CommonException(defaultMsg);
    }

    /**
     * 列表不能为空，否则就报错
     * 
     * @param list
     * @param defaultMsg
     * @param i18n
     */
    @SuppressWarnings("rawtypes")
    public static void isNotEmpty(List list, String defaultMsg, String i18n) {
        if (CollectionUtils.isEmpty(list))
            throw new CommonException(defaultMsg, i18n);
    }

    /**
     * 值不能为空，如果是空则报错
     * 
     * @param value
     * @param defaultMsg
     */
    public static void isNotBlank(String value, String defaultMsg) {
        if (StringUtils.isBlank(value))
            throw new CommonException(defaultMsg);
    }

    /**
     * 值不能为空，如果是空则报错
     * 
     * @param value
     * @param defaultMsg
     * @param i18n
     */
    public static void isNotBlank(String value, String defaultMsg, String i18n) {
        if (StringUtils.isBlank(value))
            throw new CommonException(defaultMsg, i18n);
    }

    /**
     * 判断是否已经存在，如果已存在则报错
     * 
     * @param size
     * @param code
     */
    public static void isExists(int size, String code) {
        if (size > 0)
            throw new CommonException(code + "已存在，不可重复，请修改");
    }

    /**
     * 参数不能为空，为空报错
     * 
     * @param type
     * @param mss
     */
    public static void isNotNull(Object type, String mss) {
        throw new CommonException(mss + type + "不能为空");
    }

    /**
     * 参数不能为空，为空报错
     * 
     * @param type
     */
    public static void isNotNull(Object type) {
        throw new CommonException(type + "不能为空");
    }

    /**
     * 如果是系统管理员，不允许操作
     * 
     * @param roleId
     */
    public static void isAdmin(String roleId) {
        if (roleId.equals("1"))
            throw new CommonException("系统管理员则不允许操作");

    }

    /**
     * 已经存在相同的登录账号
     * 
     * @param count
     */
    public static void theSameLoginAccount(int count) {
        if (count > 0)
            throw new CommonException("已存在相同的登录帐号");
    }

    /**
     * 找不到用户
     * 
     * @param user
     */
    public static void userIsExists(String user) {
        if (user == null)
            throw new CommonException("启用/禁用用户出错:找不到该用户", "auth:user_not_found");
    }

    /**
     * 密码不正确
     * 
     * @param list
     */
    @SuppressWarnings("rawtypes")
    public static void judgePassword(List list) {
        if (CollectionUtils.isEmpty(list))
            throw new CommonException("密码不正确!");

    }

    /**
     * id为空，保存失败
     * 
     * @param id
     */
    public static void saveFail(String id) {
        if (StringUtils.isBlank(id))
            throw new CommonException("id 为空，保存失败", "id_can_not_be_blank");

    }

    /**
     * 无法匹配系统
     * 
     * @param projectId
     */
    public static void canNotFindSystem(Object projectId) {
        if (projectId == null)
            throw new CommonException("当前操作无法匹配到对应的系统,(Can not match project's id)");

    }

    /**
     * 不合法操作
     * 
     * @param str
     */
    public static void isIllegal(String str) {
        throw new CommonException(str + "不合法");
    }

    /**
     * 值已经存在，则报错
     * 
     * @param list
     * @param value
     */
    @SuppressWarnings("rawtypes")
    public static void isExists(List list, String value) {
        if (list.size() > 0)
            throw new CommonException("已经存在相同的" + value);

    }

    /**
     * 如果不存在抛出异常
     * 
     * @param obj
     * @param str
     */
    public static void doNotExists(Object obj, String str) {
        if (obj == null)
            throw new CommonException(str + "不存在");

    }

    /**
     * 值是否重复，重复则异常
     * 
     * @param list
     * @param str
     */
    @SuppressWarnings("rawtypes")
    public static void valueRepeat(List list, String str) {
        if (list == null || list.size() > 0)
            throw new CommonException(str + "值重复");

    }

    /**
     * 值是否重复，重复则异常
     * 
     * @param list
     * @param str
     */
    @SuppressWarnings("rawtypes")
    public static void canNotDelete(List list, String str) {
        if (list != null && list.size() > 0)
            throw new CommonException(str + "不能删除");

    }
}
