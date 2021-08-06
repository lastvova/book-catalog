package com.softserve.repository;

import java.util.List;
import java.util.Optional;

public interface BasicRepository<T, I> {

    Optional<T> findById(I id);

    List<T> getAll();

    T save(T entity);

    T update(T entity);

    T delete(T entity);
}
