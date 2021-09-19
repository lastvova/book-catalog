package com.softserve.repository;

import com.softserve.util.FilteringParameters;
import com.softserve.util.PaginationParameters;
import com.softserve.util.SortingParameters;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BaseRepository<T, I> {

    T getById(I id);

    Page<T> getAll(PaginationParameters paginationParameters, SortingParameters sortingParameters, List<FilteringParameters> filteringParameters);

    T create(T entity);

    T update(T entity);

    boolean delete(I id);

}
