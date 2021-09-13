package com.softserve.util;

import com.softserve.enums.FieldType;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
public class SortingParameters {

    public static final String SORTING_PART_OF_QUERY = " order by :sortingField :sortingOrder ";
    private String sortingField;
    private String sortingOrder;

    public SortingParameters(String inputField, String inputOrder) {
        if (StringUtils.isBlank(inputField) || !EnumUtils.isValidEnumIgnoreCase(FieldType.class, inputField)) {
            inputField = FieldType.ENTITY_CREATED_DATE.toString();
        }
        this.sortingField = inputField.substring(inputField.indexOf("_") + 1);
        if (!StringUtils.isBlank(inputOrder) && inputOrder.equals("DESC")) {
            this.sortingOrder = "DESC";
        } else this.sortingOrder = "ASC";
    }
}
