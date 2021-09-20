package com.softserve.service;

import com.softserve.utils.ListParams;
import org.springframework.data.domain.Page;

public interface BaseService<T, I> {

    T getById(I id);

    Page<T> getAll(ListParams<?> listParams);

    T create(T entity);

    T update(T entity);

    boolean delete(I id);

}
