package com.mie.base.core.utils.query;

import com.mie.base.core.exception.CommonException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.std.JsonNodeDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class QueryParamWapperDeserializer extends JsonDeserializer<QueryParamWapper> {

    private static final String separator = "_$_";

    @SuppressWarnings("unused")
	private static final long PARSE_TIMEOUT = 30 * 1000L; // 解析不能超过30秒

    @SuppressWarnings("unchecked")
	@Override
    public QueryParamWapper deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException,
            JsonProcessingException {
        JsonToken currentToken = jp.getCurrentToken();
        if (currentToken != JsonToken.START_OBJECT) {
            throw new CommonException("参数格式异常，无法解析","unable_to_parse");
        }

        JsonDeserializer<ObjectNode> des = (JsonDeserializer<ObjectNode>) JsonNodeDeserializer
                .getDeserializer(ObjectNode.class);
        ObjectNode objectNode = des.deserialize(jp, ctxt);

        List<QueryParam> queryParams = new ArrayList<>();

        Iterator<String> fieldNames = objectNode.fieldNames();
        while (fieldNames.hasNext()) {
            String key = fieldNames.next();
            // String value = objectNode.get(key).toString();
            String value = null;
            JsonNode valueNode = objectNode.get(key);
            if (valueNode instanceof TextNode) {
                value = valueNode.asText();
            } else {
                value = valueNode.toString();
            }
            queryParams.add(this.newQueryParam(key, value));
        }

        QueryParamWapper wapper = null;
        if (CollectionUtils.isNotEmpty(queryParams)) {
            wapper = new QueryParamWapper();
            wapper.setQueryParams(queryParams);
        }

        return wapper;
    }

    private QueryParam newQueryParam(String key, String value) {
        if (!key.contains(separator)) {
            throw new CommonException("参数格式异常，缺少关键字[_$_]","unable_to_parse" );
        }

        String[] propertyAndCondition = key.split("_\\$_");

        if (propertyAndCondition.length < 2 || StringUtils.isBlank(propertyAndCondition[1])) {
            throw new CommonException("参数格式异常，\"[_$_]\"后面缺少查询条件","unable_to_parse" );
        }

        QueryParam queryParam = new QueryParam();
        queryParam.setProperty(propertyAndCondition[0]);
        queryParam.setCondition(propertyAndCondition[1]);
        queryParam.setValue(value);

        return queryParam;
    }

    public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
        String str = "{\"name_$_eq\":\"abc\",   \"abc_$_neq\":[\"bcd\",\"adfs\"]}";
        ObjectMapper objectMapper = new ObjectMapper();

        QueryParamWapper wapper = objectMapper.readValue(str, QueryParamWapper.class);
        System.out.println(wapper.getQueryParams());
        // System.out.println(wapper.getQueryParams().get(0));
    }

}
