package com.softserve.repository.impl;


import com.softserve.exception.IncorrectIdException;
import com.softserve.exception.WrongEntityException;
import com.softserve.repository.BaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Objects;

@Repository
@SuppressWarnings("unchecked")
public abstract class BaseRepositoryImpl<T, I> implements BaseRepository<T, I> {

    private static final Logger log = LoggerFactory.getLogger(BaseRepositoryImpl.class);
    protected final Class<T> basicClass;

    @PersistenceContext
    protected EntityManager entityManager;

    protected BaseRepositoryImpl() {
        basicClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public T getById(I id) {
        log.debug("In findById of {}", basicClass.getName());
        if (Objects.isNull(id)) {
            throw new IncorrectIdException("Wrong id = " + id +" in getById of repository");
        }
        return entityManager.find(basicClass, id);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<T> getAll() {
        log.debug("In getAll of {}", basicClass.getName());
        return entityManager
                .createQuery("from " + basicClass.getName())
                .getResultList();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public T save(T entity) {
        log.debug("In save({}) of {}", entity, basicClass.getName());
        if (!isValidEntity(entity)) {
            throw new WrongEntityException("Wrong entity in save method of repository" + entity);
        }
        entityManager.persist(entity);
        return entity;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public T update(T entity) {
        log.debug("In update({}) of {}", entity, basicClass.getName());
        if (!isValidEntity(entity)) {
            throw new WrongEntityException("Wrong entity in update method of repository" + entity);
        }
        entityManager.merge(entity);
        return entity;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public boolean delete(I id) {
        log.debug("In delete({}) of {}", id, basicClass.getName());
        T entity = getById(id);
        entityManager.remove(entity);
        return true;
    }

    protected boolean isValidEntity(T entity) {
        log.debug("In delete({}) of {}", entity, basicClass.getName());
        return true;
    }
}


