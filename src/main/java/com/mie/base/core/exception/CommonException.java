package com.mie.base.core.exception;

import com.mie.base.utils.json.JsonUtils;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 业务类异常
 *
 * @param
 *
 */
public class CommonException extends RuntimeException {

    private static final long serialVersionUID = 220200304312901149L;

    private static Logger logger = LoggerFactory.getLogger(CommonException.class);

    public static final Pattern SERIALIZE_PREFIX_PARTTERN = Pattern.compile(
            "CommonException_content_start_\\s(.*?)\\s_end_", Pattern.DOTALL);

    private static final String SERIALIZE_PREFIX = "CommonException_content_start_\n{0}\n_end_";

    private String i18nCode;

    private Object[] i18nArgs;

    private Object data;

    public CommonException() {
        super();
    }

    public CommonException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public CommonException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommonException(String message) {
        super(message);
    }

    public CommonException(String message, String i18nCode) {
        super(message);
        this.setI18nCode(i18nCode);
    }

    public CommonException(String message, String i18nCode, Object... i18nArgs) {
        super(message);
        this.setI18nArgs(i18nArgs);
        this.setI18nCode(i18nCode);
    }

    public CommonException(String message, Object data) {
        super(message);
        this.setData(data);
    }

    /*
     * public CommonException(String message, String i18nCode, Object data) { super(message);
     * this.setI18nCode(i18nCode); this.setData(data); }
     */

    public CommonException(Throwable cause) {
        super(cause);
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getI18nCode() {
        return i18nCode;
    }

    public void setI18nCode(String i18nCode) {
        this.i18nCode = i18nCode;
    }

    public Object[] getI18nArgs() {
        return i18nArgs;
    }

    public void setI18nArgs(Object[] i18nArgs) {
        this.i18nArgs = i18nArgs;
    }

    @Override
    public String toString() {
        Map<String, Object> exceptionContent = new HashMap<>();
        exceptionContent.put("i18nCode", this.getI18nCode());
        exceptionContent.put("message", this.getMessage());
        // exceptionContent.put("data", this.getData());

        try {
            String json = JsonUtils.writeValueAsString(exceptionContent);
            return MessageFormat.format(SERIALIZE_PREFIX, json);
        } catch (Exception e) {
            logger.warn("CommonException toString err", e);
        }
        return super.toString();
    }

    /**
     * 从字符串中匹配出CommonException的信息
     * 
     * @param content
     * @return
     */
    public static CommonException matchCommonException(String content) {
        if (StringUtils.isBlank(content)) {
            return null;
        }

        Matcher matcher = CommonException.SERIALIZE_PREFIX_PARTTERN.matcher(content);
        if (!matcher.find()) {
            return null;
        }

        String json = matcher.group(1);
        JsonNode treeNode;

        try {
            // ObjectMapper objectMapper = new ObjectMapper();
            // treeNode = objectMapper.readTree(json);
            treeNode = JsonUtils.getObjectMapper().readTree(json);
            if (!treeNode.isObject()) {
                return null;
            }
            String message = null;
            if (!treeNode.get("message").isNull()) {
                message = treeNode.get("message").asText();
            }

            String i18nCode = null;
            if (!treeNode.get("i18nCode").isNull()) {
                i18nCode = treeNode.get("i18nCode").asText();
            }

            return new CommonException(message, i18nCode);

        } catch (IOException e) {
            logger.warn("match CommomException from String failed", e);
            return null;
        }

    }
}
