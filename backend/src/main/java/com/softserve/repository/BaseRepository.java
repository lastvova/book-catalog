package com.softserve.repository;

import java.util.List;

public interface BaseRepository<T, I> {

    T getById(I id);

    List<T> getAll();

    T save(T entity);

    T update(T entity);

    boolean delete(I id);
}
