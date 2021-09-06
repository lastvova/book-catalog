package com.softserve.util;

import com.softserve.enums.FieldType;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
public class SortingParameters {

    private String field;
    private String order;

    public SortingParameters(String inputField, String inputOrder) {
        if (StringUtils.isBlank(inputField) || !EnumUtils.isValidEnumIgnoreCase(FieldType.class, inputField)) {
            inputField = FieldType.ENTITY_CREATED_DATE.toString();
        }
        this.field = inputField.substring(inputField.indexOf("_") + 1);
        if (!StringUtils.isBlank(inputOrder) && inputOrder.equals("DESC")) {
            this.order = "DESC";
        } else this.order = "ASC";
    }

    public String buildOrderPartOfQuery(){
        return "order by e.:sortingField :sortingOrder ";
    }

}
