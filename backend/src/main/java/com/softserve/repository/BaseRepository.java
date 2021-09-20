package com.softserve.repository;

import com.softserve.utils.ListParams;
import org.springframework.data.domain.Page;

public interface BaseRepository<T, I> {

    T getById(I id);

    Page<T> getAll(ListParams<?> params);

    T create(T entity);

    T update(T entity);

    boolean delete(I id);

}
