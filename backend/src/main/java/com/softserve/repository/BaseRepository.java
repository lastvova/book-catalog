package com.softserve.repository;

import com.softserve.util.OutputSql;

import java.util.List;

public interface BaseRepository<T, I> {

    T getById(I id);

    List<T> getAll(OutputSql params);

    T create(T entity);

    T update(T entity);

    boolean delete(I id);

    Long countRecordsInTable();
}
