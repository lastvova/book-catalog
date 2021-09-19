package com.softserve.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginationParameters {

//    Criteria api starting pagination from 0
    private int pageNumber = 0;
    private int pageSize = 5;

}
