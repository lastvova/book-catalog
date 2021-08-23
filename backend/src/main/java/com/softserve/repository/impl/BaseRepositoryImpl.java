package com.softserve.repository.impl;


import com.softserve.exception.WrongEntityException;
import com.softserve.exception.WrongInputValueException;
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
@SuppressWarnings("unchecked") // todo: better to put this annotation on methods
public abstract class BaseRepositoryImpl<T, I> implements BaseRepository<T, I> {

    private static final Logger log = LoggerFactory.getLogger(BaseRepositoryImpl.class); // todo: wrong name pattern!
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
        // todo: more easier: log.debug("{}.getById({})", id, basicClass.getName());
        log.debug("In getById method with input value: [{}] of {}", id, basicClass.getName());
        if (Objects.isNull(id)) {
            // todo: in java exist exception for this issue: IllegalArgumentException
            throw new WrongInputValueException("Wrong id = " + id); // todo: id always == null!
        }
        return entityManager.find(basicClass, id);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<T> getAll() {
        log.debug("In getAll method of {}", basicClass.getName());
        return entityManager
                .createQuery("from " + basicClass.getName())
                .getResultList();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public T save(T entity) {
        log.debug("In save method with input value: [{}] of {}", entity, basicClass.getName());
        if (isInvalidEntity(entity)) {
            throw new WrongEntityException("Wrong entity in save method " + entity);
        }
        entityManager.persist(entity);
        return entity;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public T update(T entity) {
        log.debug("In update method with input value: [{}] of {}", entity, basicClass.getName());
        if (isInvalidEntity(entity)) {
            throw new WrongEntityException("Wrong entity in update method " + entity);
        }
        // todo: what will be in case if entity.id == null ?
        entityManager.merge(entity);
        return entity;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public boolean delete(I id) {
        log.debug("In delete method with input value: [{}] of {}", id, basicClass.getName());
        if (Objects.isNull(id)) {
            throw new WrongInputValueException("Wrong id = " + id + " in delete method"); // todo: id always == null!
        }
        T entity = getById(id);
        if (Objects.isNull(entity)) {
            return false;
        }
        entityManager.remove(entity);
        return true;
    }

    protected boolean isInvalidEntity(T entity) {
        log.debug("In isInvalidEntity method with input value: [{}] of {}", entity, basicClass.getName());
        return Objects.isNull(entity);
    }
}


