package com.softserve.service.impl;

import com.softserve.exception.WrongEntityException;
import com.softserve.exception.WrongInputValueException;
import com.softserve.repository.BaseRepository;
import com.softserve.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public abstract class BaseServiceImpl<T, I> implements BaseService<T, I> {

    private static final Logger log = LoggerFactory.getLogger(BaseServiceImpl.class);
    private final BaseRepository<T, I> baseRepository;

    @Autowired
    public BaseServiceImpl(BaseRepository<T, I> baseRepository) {
        this.baseRepository = baseRepository;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public T getById(I id) {
        log.debug("Enter into getById method with input value: [{}]", id);
        if (Objects.isNull(id)) {
            throw new WrongInputValueException("Wrong entity id :" + id);
        }
        return baseRepository.getById(id);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<T> getAll() {
        log.debug("Enter into getAll method");
        return baseRepository.getAll();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public T save(T entity) {
        log.debug("Enter into save method with input value: [{}]", entity);
        if (isInvalidEntity(entity)) {
            throw new WrongEntityException("Wrong entity in save method :" + entity);
        }
        return baseRepository.save(entity);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public T update(T entity) {
        log.debug("Enter into update method with input value: [{}]", entity);
        if (isInvalidEntity(entity)) {
            throw new WrongEntityException("Wrong entity in update method :" + entity);
        }
        return baseRepository.update(entity);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean delete(I id) {
        log.debug("Enter into delete method with input value: [{}]", id);
        if (Objects.isNull(id)) {
            throw new WrongInputValueException("Wrong entity id :" + id);
        }
        return baseRepository.delete(id);
    }

    protected boolean isInvalidEntity(T entity) {
        return Objects.isNull(entity);
    }
}
