package com.softserve.service;

import com.softserve.util.FilteringParameters;
import com.softserve.util.PaginationAndSortingParameters;
import org.springframework.data.domain.Page;

public interface BaseService<T, I> {

    T getById(I id);

    Page<T> getAll(PaginationAndSortingParameters paginationAndSortingParameters, FilteringParameters filteringParameters);

    T create(T entity);

    T update(T entity);

    boolean delete(I id);

}
