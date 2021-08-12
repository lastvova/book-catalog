package com.softserve.repository.impl;


import com.softserve.repository.BasicRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@SuppressWarnings("unchecked")
public abstract class BasicRepositoryImpl<T, I> implements BasicRepository<T, I> {
    //TODO session in constructor
//    TODO change log type(debug, warn, etc..)
    protected final Class<T> basicClass;

    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    public BasicRepositoryImpl() {
        basicClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<T> findById(I id) {
        log.info("In findById of {}", basicClass.getName());
        T entity = entityManager.find(basicClass, id);
        return Optional.ofNullable(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> getAll() {
        log.info("In getAll of {}", basicClass.getName());
        return entityManager.createQuery("from " + basicClass.getName())
                .getResultList();
    }

    @Override
    @Transactional
    public T save(T entity) {
        log.info("In save({}) of {}", entity, basicClass.getName());
        entityManager.persist(entity);
        return entity;
    }

    @Override
    @Transactional
    public T update(T entity) {
        log.info("In update({}) of {}", entity, basicClass.getName());
        entityManager.refresh(entity);
        return entity;
    }

    @Override
    @Transactional
    public T delete(I id) {
        log.info("In delete({}) of {}", id, basicClass.getName());
        T entity = entityManager.find(basicClass, id);
        entityManager.remove(entity);
        return entity;
    }
}


