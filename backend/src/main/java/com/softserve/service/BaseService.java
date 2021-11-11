package com.softserve.service;

import com.softserve.entity.interfaces.GeneralMethodsInterface;
import com.softserve.utils.ListParams;
import org.springframework.data.domain.Page;

import java.io.Serializable;

public interface BaseService<T extends GeneralMethodsInterface<I>, I extends Serializable> {

    T getById(I id);

    Page<T> getAll(ListParams<?> listParams);

    T create(T entity);

    T update(T entity);

    boolean delete(I id);

}
