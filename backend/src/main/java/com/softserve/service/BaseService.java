package com.softserve.service;

import com.softserve.util.OutputSql;
import com.softserve.util.SearchResult;

public interface BaseService<T, I> {

    T getById(I id);

    SearchResult<T> getAll(OutputSql params);

    T create(T entity);

    T update(T entity);

    boolean delete(I id);

}
