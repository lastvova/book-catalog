package com.softserve.repository.impl;


import com.softserve.repository.BasicRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@SuppressWarnings("unchecked")
public abstract class BasicRepositoryImpl<T extends Serializable, I extends Serializable> implements BasicRepository<T, I> {

    protected final Class<T> basicClass;

    @Autowired
    protected SessionFactory sessionFactory;

    @Autowired
    public BasicRepositoryImpl() {
        basicClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    @Override
    @Transactional
    public Optional<T> findById(I id) {
        log.info("In findById of {}", basicClass.getName());
        return Optional.ofNullable(sessionFactory.getCurrentSession().get(basicClass, id));
    }

    @Override
    public List<T> getAll() {
        log.info("In getAll of {}", basicClass.getName());
        return sessionFactory.getCurrentSession()
                .createQuery("from " + basicClass.getName())
                .getResultList();
    }

    @Override
    @Transactional
    public T save(T entity) {
        log.info("In save({}) of {}", entity, basicClass.getName());
        sessionFactory.getCurrentSession().save(entity);
        return entity;
    }

    @Override
    public T update(T entity) {
        log.info("In update({}) of {}", entity, basicClass.getName());
        sessionFactory.getCurrentSession().update(entity);
        return entity;
    }

    @Override
    public T delete(T entity) {
        log.info("In delete({}) of {}", entity, basicClass.getName());
        sessionFactory.getCurrentSession().delete(entity);
        return entity;
    }
}


