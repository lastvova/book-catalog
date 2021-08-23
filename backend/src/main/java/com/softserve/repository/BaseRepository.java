package com.softserve.repository;

import java.util.List;

// todo: where method for pagination with filtering? (method for grid) (did not see it here and in inherited classes)
public interface BaseRepository<T, I> {

    T getById(I id);

    List<T> getAll();

    T save(T entity); // todo: need to rename to "create"

    T update(T entity);

    boolean delete(I id);
}
