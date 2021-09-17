package com.softserve.util;

import com.softserve.enums.EntityFields;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilteringParameters {

    private EntityFields filterBy;
    private String filterValue;

}
