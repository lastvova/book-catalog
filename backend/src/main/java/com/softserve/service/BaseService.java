package com.softserve.service;

import java.util.List;

public interface BaseService<T, I> {

    T getById(I id);

    List<T> getAll();

    T create(T entity);

    T update(T entity);

    boolean delete(I id);

}
