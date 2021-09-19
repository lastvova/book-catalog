package com.softserve.service.impl;

import com.softserve.exception.EntityNotFoundException;
import com.softserve.exception.WrongEntityException;
import com.softserve.repository.BaseRepository;
import com.softserve.service.BaseService;
import com.softserve.util.FilteringParameters;
import com.softserve.util.PaginationParameters;
import com.softserve.util.SortingParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public abstract class BaseServiceImpl<T, I> implements BaseService<T, I> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseServiceImpl.class);
    private final BaseRepository<T, I> baseRepository;

    @Autowired
    protected BaseServiceImpl(BaseRepository<T, I> baseRepository) {
        this.baseRepository = baseRepository;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public T getById(I id) {
        LOGGER.debug("getById({})", id);
        if (Objects.isNull(id)) {
            throw new IllegalStateException("Wrong entity id");
        }
        T entity = baseRepository.getById(id);
        if (Objects.isNull(entity)) {
            throw new EntityNotFoundException("Not found entity with id: " + id);
        }
        return entity;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<T> getAll(PaginationParameters paginationParameters, SortingParameters sortingParameters, FilteringParameters filteringParameters) {
        LOGGER.debug("getAll()");
        return baseRepository.getAll(paginationParameters, sortingParameters, filteringParameters);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public T create(T entity) {
        LOGGER.debug("create({})", entity);
        if (isInvalidEntity(entity)) {
            throw new WrongEntityException("Wrong entity in save method :" + entity);
        }
        return baseRepository.create(entity);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public T update(T entity) {
        LOGGER.debug("update({})", entity);
        if (isInvalidEntity(entity)) {
            throw new WrongEntityException("Wrong entity in update method :" + entity);
        }
        return baseRepository.update(entity);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean delete(I id) {
        LOGGER.debug("delete({})", id);
        if (Objects.isNull(id)) {
            throw new IllegalStateException("Wrong entity id");
        }
        return baseRepository.delete(id);
    }

    protected boolean isInvalidEntity(T entity) {
        LOGGER.debug("inInvalidEntity({})", entity);
        return Objects.isNull(entity);
    }
}
