package com.softserve.service;

import com.softserve.util.OutputSql;

import java.util.List;

public interface BaseService<T, I> {

    T getById(I id);

    List<T> getAll(OutputSql params);

    T create(T entity);

    T update(T entity);

    boolean delete(I id);

}
