package com.softserve.util;

import lombok.Getter;
import lombok.Setter;

import java.util.NoSuchElementException;
import java.util.Objects;

@Getter
@Setter
public class PaginationParameters {

    private Integer currentPage;
    private Integer recordsPerPage;
    private Integer startFrom;
    private Integer totalRecords;
    private Integer totalPages;

    public PaginationParameters(Integer currentPage, Integer recordsPerPage) {
        if (Objects.isNull(currentPage) || currentPage <= 0) {
            this.currentPage = 1;
        } else {
            this.currentPage = currentPage;
        }
        if (Objects.isNull(recordsPerPage)) {
            this.recordsPerPage = 5;
        } else {
            this.recordsPerPage = recordsPerPage;
        }
    }

    public String buildPaginationPartOfQuery(Integer totalRecords) {
        this.totalRecords = totalRecords;
        this.totalPages = (totalRecords % recordsPerPage) == 0 ? totalRecords / recordsPerPage : totalRecords / recordsPerPage + 1;
        this.startFrom = currentPage * recordsPerPage - recordsPerPage;
        if (totalRecords <= startFrom) {
            throw new NoSuchElementException("Wrong input value for pagination, no such elements");
        }
        return "limit :recordsPerPage offset :startFrom";
    }
}
