package com.softserve.service;

import java.util.List;

public interface BasicService<T, I> {

    T findById(I id);

    List<T> getAll();

    T save(T entity);

    T update(T entity);

    T delete(I id);
}
