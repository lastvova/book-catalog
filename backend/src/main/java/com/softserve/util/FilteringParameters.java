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

    private String filteringField;
    private String filteringValue;
    private String filteringOperator;

    public FilteringParameters(String filteringField, String filteringValue, String filteringOperator) {
        if (StringUtils.isBlank(filteringField) || !EnumUtils.isValidEnumIgnoreCase(FieldType.class, filteringField)) {
            throw new IllegalStateException("Wrong input field for filtering");
        }
        this.filteringField = filteringField.substring(filteringField.indexOf("_") + 1);
        if (StringUtils.isBlank(filteringValue)) {
            throw new IllegalStateException("Wrong input value for filtering");
        }
        this.filteringValue = filteringValue;
        if (StringUtils.isBlank(filteringOperator) || !EnumUtils.isValidEnumIgnoreCase(FilteringOperator.class, filteringOperator)) {
            throw new IllegalStateException("Wrong input operator for filtering");
        }
        this.filteringOperator = filteringOperator;
    }
}
