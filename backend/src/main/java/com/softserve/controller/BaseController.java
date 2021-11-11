package com.softserve.controller;

import com.softserve.enums.OrderSort;
import com.softserve.utils.ListParams;

public abstract class BaseController {
//TODO remove to pojo
    protected ListParams validatePageAndSortParameters(ListParams<?> params){
        setPageParameters(params);
        setSortParameters(params);
        return params;
    }

    private void setPageParameters(ListParams<?> params) {
        if (params.getPageNumber() == null) {
            params.setPageNumber(0);
        }
        if (params.getPageSize() == null) {
            params.setPageSize(5);
        }
    }

    private void setSortParameters(ListParams<?> params) {
        if (params.getSortField() == null) {
            params.setSortField("rating");
        }
        if (params.getOrder() == null) {
            params.setOrder(OrderSort.ASC);
        }
    }
}
