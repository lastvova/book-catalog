package com.softserve.repository.impl;


import com.softserve.repository.BaseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@SuppressWarnings("unchecked")
public abstract class BaseRepositoryImpl<T, I> implements BaseRepository<T, I> {
    protected final Class<T> basicClass;

    @PersistenceContext
    protected EntityManager entityManager;

    protected BaseRepositoryImpl() {
        basicClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    @Override
    public Optional<T> findById(I id) {
        log.info("In findById of {}", basicClass.getName());
        T entity = entityManager.find(basicClass, id);
        return Optional.ofNullable(entity);
    }

    @Override
    public List<T> getAll() {
        log.info("In getAll of {}", basicClass.getName());
        return entityManager
                .createQuery("from " + basicClass.getName())
                .getResultList();
    }

    @Override
    public T save(T entity) {
        log.info("In save({}) of {}", entity, basicClass.getName());
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public T update(T entity) {
        log.info("In update({}) of {}", entity, basicClass.getName());
        entityManager.merge(entity);
        return entity;
    }

    @Override
    public T delete(T entity) {
        log.info("In delete({}) of {}", entity, basicClass.getName());
        entityManager.remove(entity);
        return entity;
    }
}


