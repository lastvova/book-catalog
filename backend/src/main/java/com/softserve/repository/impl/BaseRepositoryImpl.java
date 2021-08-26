package com.softserve.repository.impl;


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
public abstract class BaseRepositoryImpl<T, I> implements BaseRepository<T, I> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseRepositoryImpl.class);
    protected final Class<T> basicClass;

    @PersistenceContext
    protected EntityManager entityManager;

    @SuppressWarnings("unchecked")
    protected BaseRepositoryImpl() {
        basicClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public T getById(I id) {
        LOGGER.debug("{}.getById({})", basicClass.getName(), id);
        if (Objects.isNull(id)) {
            throw new IllegalStateException("Wrong id");
        }
        return entityManager.find(basicClass, id);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<T> getAll() {
        LOGGER.debug("{}.getAll", basicClass.getName());
        return entityManager
                .createQuery("from " + basicClass.getName(), basicClass)
                .getResultList();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public T create(T entity) {
        LOGGER.debug("{}.create({})", basicClass.getName(), entity);
        if (isInvalidEntity(entity)) {
            throw new WrongEntityException("Wrong entity in save method " + entity);
        }
        entityManager.persist(entity);
        return entity;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public T update(T entity) {
        LOGGER.debug("{}.update({})", basicClass.getName(), entity);
        if (isInvalidEntityId(entity)) {
            throw new IllegalStateException("Wrong entity id");
        }
        if (isInvalidEntity(entity)) {
            throw new WrongEntityException("Wrong entity in update method " + entity);
        }
        entityManager.merge(entity);
        return entity;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public boolean delete(I id) {
        LOGGER.debug("{}.delete({})", basicClass.getName(), id);
        if (Objects.isNull(id)) {
            throw new IllegalStateException("Wrong id in delete method");
        }
        T entity = getById(id);
        if (Objects.isNull(entity)) {
            return false;
        }
        entityManager.remove(entity);
        return true;
    }

    protected boolean isInvalidEntity(T entity) {
        LOGGER.debug("{}.isInvalidEntity({})", basicClass.getName(), entity);
        return Objects.isNull(entity);
    }

    protected boolean isInvalidEntityId(T entity) {
        LOGGER.debug("{}.isInvalidEntityId({})", basicClass.getName(), entity);
        return false;
    }
}


