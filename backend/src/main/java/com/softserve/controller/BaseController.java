package com.softserve.controller;

import com.softserve.enums.EntityFields;
import com.softserve.util.FilteringParameters;
import com.softserve.util.PaginationParameters;
import com.softserve.util.SortingParameters;
import org.springframework.data.domain.Sort;

public abstract class BaseController {

    protected PaginationParameters setPageParameters(Integer page, Integer size) {
        PaginationParameters paginationParameters = new PaginationParameters();
        paginationParameters.setPageSize(size);
        paginationParameters.setPageNumber(page);
        return paginationParameters;
    }

    protected SortingParameters setSortParameters(String sortBy, String order) {
        SortingParameters sortingParameters = new SortingParameters();
        if (order != null) {
            sortingParameters.setSortDirection(Sort.Direction.fromString(order));
        }
        if (sortBy != null) {
            sortingParameters.setSortBy(sortBy);
        }
        return sortingParameters;
    }

    protected FilteringParameters setFilterParameters(String filterBy, String filterValue) {
        FilteringParameters filteringParameters = new FilteringParameters();
        if (filterBy != null || filterValue != null) {
            filteringParameters.setFilterBy(EntityFields.valueOf(filterBy));
            filteringParameters.setFilterValue(filterValue);
        }
        return filteringParameters;
    }
}
