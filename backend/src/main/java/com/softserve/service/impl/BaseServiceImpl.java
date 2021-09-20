package com.softserve.service.impl;

import com.softserve.exception.EntityNotFoundException;
import com.softserve.exception.WrongEntityException;
import com.softserve.repository.BaseRepository;
import com.softserve.service.BaseService;
import com.softserve.utils.ListParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public abstract class BaseServiceImpl<T, I> implements BaseService<T, I> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseServiceImpl.class);
    private final BaseRepository<T, I> baseRepository;

    @Autowired
    protected BaseServiceImpl(BaseRepository<T, I> baseRepository) {
        this.baseRepository = baseRepository;
    }

    @Override
    public T getById(I id) {
        LOGGER.debug("getById({})", id);
        if (id == null) {
            throw new IllegalStateException("Wrong entity id");
        }
        T entity = baseRepository.getById(id);
        if (entity == null) {
            throw new EntityNotFoundException("Not found entity with id: " + id);
        }
        return entity;
    }

    @Override
    public Page<T> getAll(ListParams<?> listParams) {
        LOGGER.debug("getAll()");
        return baseRepository.getAll(listParams);
    }

    @Override
    @Transactional
    public T create(T entity) {
        LOGGER.debug("create({})", entity);
        if (isInvalidEntity(entity)) {
            throw new WrongEntityException("Wrong entity in save method :" + entity);
        }
        return baseRepository.create(entity);
    }

    @Override
    @Transactional
    public T update(T entity) {
        LOGGER.debug("update({})", entity);
        if (isInvalidEntity(entity)) {
            throw new WrongEntityException("Wrong entity in update method :" + entity);
        }
        return baseRepository.update(entity);
    }

    @Override
    @Transactional
    public boolean delete(I id) {
        LOGGER.debug("delete({})", id);
        if (id == null) {
            throw new IllegalArgumentException("Wrong entity id");
        }
        return baseRepository.delete(id);
    }

    protected boolean isInvalidEntity(T entity) {
        LOGGER.debug("inInvalidEntity({})", entity);
        return entity == null;
    }
}
