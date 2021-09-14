package com.softserve.util;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchResult<T> {

    private Long totalRecords;
    private List<T> data;

    public SearchResult(Long totalRecords, List<T> data) {
        this.totalRecords = totalRecords;
        this.data = data;
    }
}
