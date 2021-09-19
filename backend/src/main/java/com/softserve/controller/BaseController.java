package com.softserve.controller;

import com.softserve.enums.EntityFields;
import com.softserve.util.FilteringParameters;
import com.softserve.util.PaginationParameters;
import com.softserve.util.SortingParameters;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;

import java.util.Objects;

public abstract class BaseController {

    protected PaginationParameters setPageParameters(Integer page, Integer size) {
        PaginationParameters paginationParameters = new PaginationParameters();
        paginationParameters.setPageSize(size);
        paginationParameters.setPageNumber(page);
        return paginationParameters;
    }

    protected SortingParameters setSortParameters(String sortBy, String order) {
        SortingParameters sortingParameters = new SortingParameters();
        if (Objects.nonNull(order)) {
            sortingParameters.setSortDirection(Sort.Direction.fromString(order));
        }
        if (Objects.nonNull(sortBy)) {
            sortingParameters.setSortBy(sortBy);
        }
        return sortingParameters;
    }

    protected FilteringParameters setFilterParameters(String filterBy, String filterValue) {
        FilteringParameters filteringParameters = new FilteringParameters();
        if (StringUtils.isBlank(filterBy) || StringUtils.isBlank(filterValue)) {
            throw new IllegalArgumentException("Wrong filter parameters");
        }
        filteringParameters.setFilterBy(EntityFields.valueOf(filterBy));
        filteringParameters.setFilterValue(filterValue);
        return filteringParameters;
    }
}
