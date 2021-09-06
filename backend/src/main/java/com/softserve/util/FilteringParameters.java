package com.softserve.util;

import com.softserve.enums.FieldType;
import com.softserve.enums.FilteringOperator;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
public class FilteringParameters {

    private String field;
    private String value;
    private String operator;

    public FilteringParameters(String inputField, String value, String operator) {
        if (StringUtils.isBlank(inputField) || !EnumUtils.isValidEnumIgnoreCase(FieldType.class, inputField)) {
            throw new IllegalStateException("Wrong input field for filtering");
        }
        this.field = inputField.substring(inputField.indexOf("_") + 1);
        if (StringUtils.isBlank(value)) {
            throw new IllegalStateException("Wrong input value for filtering");
        }
        this.value = value;
        if (StringUtils.isBlank(operator) || !EnumUtils.isValidEnumIgnoreCase(FilteringOperator.class, operator)) {
            throw new IllegalStateException("Wrong input operator for filtering");
        }
        this.operator = operator;
    }
}
