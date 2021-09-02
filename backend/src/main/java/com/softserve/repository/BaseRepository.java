package com.softserve.repository;

import com.softserve.util.OutputSql;

import java.util.List;

// todo: where method for pagination with filtering? (method for grid) (did not see it here and in inherited classes)
public interface BaseRepository<T, I> {

    T getById(I id);

    List<T> getAll();

    T create(T entity);

    T update(T entity);

    boolean delete(I id);

    List<T> getAllByParams(OutputSql params);

    Long countRecordsInTable();
}
