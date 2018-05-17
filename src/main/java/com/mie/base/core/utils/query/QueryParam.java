package com.mie.base.core.utils.query;

import com.mie.base.core.exception.CommonException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

@ApiModel("通用查询条件")
public class QueryParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotEmpty
    @ApiModelProperty("属性名")
    public String property;

    @NotEmpty
    @ApiModelProperty("查询条件包括: = (eq),!= (neq), > (gt), >= (gte), <(lt), <=(lte), like, notLike, isNotNull, isNull, in")
    public String condition;

    @NotEmpty
    @ApiModelProperty("属性值")
    public String value;

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property == null ? null : property.trim();
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition == null ? null : condition.trim();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public String getFullCondition() {
        switch (this.getCondition()) {
        case "like":
        case "notLike":
        case "isNotNull":
        case "isNull":
        case "in":
            return this.getCondition();
        case "=":
        case "eq":
            return QueryConditionEnum.EQUAL_TO.getFullName();
        case "!=":
        case "neq":
            return QueryConditionEnum.NOT_EQUAL_TO.getFullName();
        case ">":
        case "gt":
            return QueryConditionEnum.GREATER_THAN.getFullName();
        case ">=":
        case "gte":
            return QueryConditionEnum.GREATER_THAN_OR_EQUAL_TO.getFullName();
        case "<":
        case "lt":
            return QueryConditionEnum.LESS_THAN.getFullName();
        case "<=":
        case "lte":
            return QueryConditionEnum.LESS_THAN_OR_EQUAL_TO.getFullName();

        default:
            throw new CommonException("condition不合法","base_illegal_opertion");
        }
    }

    @Override
    public String toString() {
        return "QueryParam [property=" + property + ", condition=" + condition + ", value=" + value + "]";
    }

}
