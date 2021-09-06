package com.softserve.util;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchResult<T> {

    private PaginationParameters paginationParams;
    private List<T> data;
}
