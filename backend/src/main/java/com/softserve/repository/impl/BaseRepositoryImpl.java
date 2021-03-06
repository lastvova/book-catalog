package com.softserve.repository.impl;


import com.softserve.entity.interfaces.GeneralMethodsInterface;
import com.softserve.enums.OrderSort;
import com.softserve.repository.BaseRepository;
import com.softserve.utils.ListParams;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

@Repository
public abstract class BaseRepositoryImpl<T extends GeneralMethodsInterface<I>, I extends Serializable> implements BaseRepository<T, I> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseRepositoryImpl.class);
    protected final Class<T> basicClass;

    @PersistenceContext
    protected EntityManager entityManager;
    protected CriteriaBuilder criteriaBuilder;

    @SuppressWarnings("unchecked")
    protected BaseRepositoryImpl() {
        basicClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0];

    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public T getById(I id) {
        LOGGER.debug("getById({})", id);
        if (id == null) {
            throw new IllegalArgumentException("Wrong id");
        }
        criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(basicClass);
        Root<T> root = criteriaQuery.from(basicClass);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("id"), id));
        TypedQuery<T> query = entityManager.createQuery(criteriaQuery);
        return query.getSingleResult();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public T create(T entity) {
        LOGGER.debug("create({})", entity);
        if (isInvalidEntity(entity) || entity.getId() != null) {
            throw new IllegalArgumentException("Wrong entity in save method ");
        }
        entityManager.persist(entity);
        return entity;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public T update(T entity) {
        LOGGER.debug("update({})", entity);
        if (entity.getId() == null || isInvalidEntity(entity)) {
            throw new IllegalArgumentException("Wrong entity in update method " + entity);
        }
        entityManager.merge(entity);
        return entity;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public boolean delete(I id) {
        LOGGER.debug("delete({})", id);
        if (id == null) {
            throw new IllegalArgumentException("Wrong id in delete method");
        }
        T entity = getById(id);
        if (entity == null) {
            return false; // todo: why false ?
        }
        entityManager.remove(entity);
        return true;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<T> getAll(ListParams<?> listParams) {
        LOGGER.debug("getAll");
        criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(basicClass);
        Root<T> root = criteriaQuery.from(basicClass);
        Predicate predicate = getPredicate(listParams, root);
        criteriaQuery.where(predicate);
        criteriaQuery.groupBy(root);
        setOrder(listParams, criteriaQuery, root);

        TypedQuery<T> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(listParams.getPageNumber() * listParams.getPageSize());
        typedQuery.setMaxResults(listParams.getPageSize());

        Pageable pageable = PageRequest.of(listParams.getPageNumber(), listParams.getPageSize());

        long entityCount = getEntityCount(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, entityCount);
    }

    public abstract Predicate getPredicate(ListParams<?> listParams, Root<T> entityRoot);


    private void setOrder(ListParams<?> listParams, CriteriaQuery<T> criteriaQuery, Root<T> entityRoot) {
        if (StringUtils.isBlank(listParams.getSortField())) {
            listParams.setSortField("createdDate");
            listParams.setOrder(OrderSort.DESC);
        }
        if (listParams.getOrder() != null && listParams.getOrder().toString().equals("ASC")) {
            criteriaQuery.orderBy(criteriaBuilder.asc(entityRoot.get(listParams.getSortField())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(entityRoot.get(listParams.getSortField())));
        }
    }

    protected long getEntityCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<T> countRoot = countQuery.from(basicClass);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }

    protected boolean isInvalidEntity(T entity) {
        LOGGER.debug("isInvalidEntity({})", entity);
        return entity == null;
    }
}


