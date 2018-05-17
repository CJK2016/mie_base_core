package com.mie.base.core.utils;

import com.mie.base.core.exception.CommonException;
import com.mie.base.core.utils.query.QueryConditionEnum;
import com.mie.base.core.utils.query.QueryParam;
import com.mie.base.core.utils.query.QueryParamWapper;
import com.mie.base.utils.json.JsonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/***
 * 使用反射的机制，根据QueryParam 生成查询条件
 *
 */
public class CriteriaUtils {

    /**
     * 借助Criteria链式结构，通过反射，设置查询条件
     * 
     * @param criteria 查询条件容器
     * @param wapper 查询条件的值
     * @return
     */
    public static <Criteria> Criteria addCriterion(Criteria criteria, QueryParamWapper wapper) {
        if (wapper == null || CollectionUtils.isEmpty(wapper.getQueryParams())) {
            return criteria;
        }
        return addCriterion(criteria, wapper.getQueryParams());
    }

    /**
     * 借助Criteria链式结构，通过反射，设置查询条件
     * 
     * @param criteria
     * @param queryParams
     * @return
     */
    public static <Criteria> Criteria addCriterion(Criteria criteria, List<QueryParam> queryParams) {

        Class<? extends Object> criClass = criteria.getClass();
        if (!criClass.getCanonicalName().contains("Criteria")) {
            throw new IllegalArgumentException("这个方法只适合 Example的 Criteria");
        }

        if (CollectionUtils.isEmpty(queryParams)) {
            return criteria;
        }

        Method[] methods = criClass.getMethods();
        for (QueryParam queryParam : queryParams) {
            if (StringUtils.isBlank(queryParam.getProperty())) {
                throw new CommonException("查询属性名不能为空", "base_canot_be_null");
            }
            if (StringUtils.isBlank(queryParam.getCondition())) {
                throw new CommonException("查询条件不能为空", "base_canot_be_null");
            }

            if (queryParam.getCondition().equals(QueryConditionEnum.LIKE.getShortName())) {
                String value = queryParam.getValue();
                if (!value.startsWith("%") && !value.endsWith("%")) {
                    queryParam.setValue("%" + value + "%");
                }
            }

            StringBuffer methodName = new StringBuffer("and");
            methodName.append(StringUtils.capitalize(queryParam.getProperty()));
            methodName.append(StringUtils.capitalize(queryParam.getFullCondition()));

            List<String> existMethodName = new ArrayList<>();
            for (Method method : methods) {
                existMethodName.add(method.getName());

                if (!method.getName().equals(methodName.toString())) {
                    continue;
                }

//                String param = queryParam.getValue();
                String value = handleValueToDate(method, queryParam);

                criteria = invokeCriteriaMethod(criteria, method, value);
            }

            if (!existMethodName.contains(methodName.toString())) {
                throw new CommonException("查询条件[" + methodName + "]不存在", "do_not_exists");
            }

        }

        return criteria;
    }

    public static String handleValueToDate(Method method, QueryParam queryParam) {
        String param = queryParam.getValue();
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length == 1) {
            Class<?> paramType = parameterTypes[0];
            if (paramType.equals(Date.class)) {
                if (!StringUtils.isNumeric(param)) {
                    throw new CommonException("时间参数需要传时间戳", "query_condition_cannot_be_empty");
                }
                if (queryParam.getFullCondition().equals("lessThanOrEqualTo")) {
                    Date d = new Date(Long.valueOf(param.trim()));
                    GregorianCalendar gc = new GregorianCalendar();
                    gc.setTime(d);
                    if ((gc.get(gc.MINUTE) == 0) && (gc.get(gc.SECOND) == 0) && (gc.get(gc.HOUR)) == 0) {
                        long parseLong = Long.parseLong(param)+86399000;
                        String valueOf = String.valueOf(parseLong);
                        queryParam.setValue(valueOf);
                        param = valueOf;
                    }
                    
                }
            }
        }
        return param;

    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	private static <Criteria> Criteria invokeCriteriaMethod(Criteria cri, Method method, String value) {
        try {
            Class<?>[] paramTypes = method.getParameterTypes();
            if (paramTypes == null || paramTypes.length == 0) {
                return (Criteria) method.invoke(cri);
            }

            if (value == null) {
                throw new CommonException("查询条件中，参数不能为空", "query_condition_cannot_be_empty");
            }
            //
            if (paramTypes.length == 1) {
                Class<?> paramType = paramTypes[0];
                if (paramType.equals(String.class)) {
                    if(value.length()>2000){
                        throw new CommonException("输入的参数长度超标", "database_length_error");
                    }
                    return (Criteria) method.invoke(cri, value);

                }
                if (paramType.equals(List.class)) {
                    List paramList = JsonUtils.convertValue(value, List.class);
                    if (CollectionUtils.isEmpty(paramList)) {
                        return cri;
                    }
                    return (Criteria) method.invoke(cri, paramList);

                }
                if (paramType.equals(Date.class)) {
                    if (StringUtils.isBlank(value) || !StringUtils.isNumeric(value)) {
                        throw new CommonException("时间参数需要传时间戳", "query_condition_cannot_be_empty");
                    }

                    return (Criteria) method.invoke(cri, new Date(Long.valueOf(value.trim())));

                } else {
                    return (Criteria) method.invoke(cri, JsonUtils.convertValue(value, paramType));
                }
            }

            // 对于要传入多个参数的处理，暂时还没有完善
            // TODO 完善类似与between，要传入多个参数的处理

            String[] paramArray = new String[paramTypes.length];
            ObjectMapper objectMapper = JsonUtils.getObjectMapper();
            ArrayNode jsonArray = (ArrayNode) objectMapper.readTree(value);
            if (jsonArray.size() != paramTypes.length) {
                throw new CommonException("查询条件中，参数数量有误，应该是[" + paramTypes.length + "]，实际是["
                        + jsonArray.size() + "]", "quantity_error");
            }

            for (int i = 0; i < jsonArray.size(); i++) {
                paramArray[i] = jsonArray.get(i).asText();
            }

            return (Criteria) method.invoke(cri, paramArray);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CommonException(e.getMessage());

        }

    }

    /*
     * public static void main(String[] args) throws JsonProcessingException, IOException {
     * ObjectMapper objectMapper = new ObjectMapper(); ArrayNode jsonArray = (ArrayNode)
     * objectMapper.readTree("[1,3,4]"); for(int i=0; i<jsonArray.size(); i++){ // paramArray[i] =
     * jsonArray.get(i).asText(); System.out.println(jsonArray.get(i).asText()); } }
     */

}
