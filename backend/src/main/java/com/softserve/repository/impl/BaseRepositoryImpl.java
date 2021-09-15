package com.softserve.repository.impl;


import com.softserve.exception.WrongEntityException;
import com.softserve.repository.BaseRepository;
import com.softserve.util.FilteringParameters;
import com.softserve.util.PaginationAndSortingParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public abstract class BaseRepositoryImpl<T, I> implements BaseRepository<T, I> {

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
        LOGGER.debug("{}.getById({})", basicClass.getName(), id);
        if (Objects.isNull(id)) {
            throw new IllegalStateException("Wrong id");
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

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<T> getAll(PaginationAndSortingParameters paginationAndSortingParameters,
                          FilteringParameters filteringParameters) {
        LOGGER.debug("{}.getAll", basicClass.getName());
        criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(basicClass);
        Root<T> root = criteriaQuery.from(basicClass);
        Predicate predicate = getPredicate(filteringParameters, root);
        criteriaQuery.where(predicate);
        setOrder(paginationAndSortingParameters, criteriaQuery, root);

        TypedQuery<T> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(paginationAndSortingParameters.getPageNumber() * paginationAndSortingParameters.getPageSize());
        typedQuery.setMaxResults(paginationAndSortingParameters.getPageSize());

        Pageable pageable = getPageable(paginationAndSortingParameters);

        long entityCount = getEntityCount(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, entityCount);
    }

    private Predicate getPredicate(FilteringParameters filteringParameters,
                                   Root<T> entityRoot) {
        List<Predicate> predicates = new ArrayList<>();
        if (Objects.nonNull(filteringParameters.getFilterBy())) {
            predicates.add(
                    criteriaBuilder.like(entityRoot.get(filteringParameters.getFilterBy()
                            .substring(filteringParameters.getFilterBy().indexOf("_") + 1)), "%"
                            + filteringParameters.getFilterValue() + "%")
            );
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void setOrder(PaginationAndSortingParameters paginationAndSortingParameters,
                          CriteriaQuery<T> criteriaQuery,
                          Root<T> entityRoot) {
        if (paginationAndSortingParameters.getSortDirection().equals(Sort.Direction.ASC)) {
            criteriaQuery.orderBy(criteriaBuilder.asc(entityRoot.get(paginationAndSortingParameters.getSortBy().substring(paginationAndSortingParameters.getSortBy().indexOf("_") + 1))));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(entityRoot.get(paginationAndSortingParameters.getSortBy().substring(paginationAndSortingParameters.getSortBy().indexOf("_") + 1))));
        }
    }

    private Pageable getPageable(PaginationAndSortingParameters paginationAndSortingParameters) {
        Sort sort = Sort.by(paginationAndSortingParameters.getSortDirection(), paginationAndSortingParameters.getSortBy());
        return PageRequest.of(paginationAndSortingParameters.getPageNumber(), paginationAndSortingParameters.getPageSize(), sort);
    }

    private long getEntityCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<T> countRoot = countQuery.from(basicClass);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
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


