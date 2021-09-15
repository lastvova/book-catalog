package com.softserve.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

import java.util.Objects;

@Getter
@Setter
public class PaginationParameters {

    public static final String PAGINATION_PART_OF_QUERY = "limit :recordsPerPage offset :startFrom";
    private Integer currentPage;
    private Integer recordsPerPage;
    private Integer startFrom;
    private Integer totalRecords;
    private Integer totalPages;

    private int pageNumber = 0;
    private int pageSize = 5;
    private Sort.Direction sortDirection = Sort.Direction.ASC;
    private String sortBy = "crated_date";

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
}
