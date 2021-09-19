package com.softserve.repository.impl;


import com.softserve.exception.WrongEntityException;
import com.softserve.repository.BaseRepository;
import com.softserve.util.FilteringParameters;
import com.softserve.util.PaginationParameters;
import com.softserve.util.SortingParameters;
import org.apache.commons.collections4.CollectionUtils;
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
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.ParameterizedType;
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
    protected List<Predicate> predicates;

    @SuppressWarnings("unchecked")
    protected BaseRepositoryImpl() {
        basicClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0];

    }

    @Override
    public T getById(I id) {
        LOGGER.debug("getById({})", id);
        //TODO should be part of validating methods
        if (Objects.isNull(id)) {
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
        if (isInvalidEntity(entity)) {
            throw new WrongEntityException("Wrong entity in save method " + entity);
        }
        entityManager.persist(entity);
        return entity;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public T update(T entity) {
        LOGGER.debug("update({})", entity);
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
        LOGGER.debug("delete({})", id);
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
    public Page<T> getAll(PaginationParameters paginationParameters, SortingParameters sortingParameters,
                          List<FilteringParameters> filteringParameters) {
        LOGGER.debug("getAll");
        criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(basicClass);
        Root<T> root = criteriaQuery.from(basicClass);
        Predicate predicate = getPredicate(filteringParameters, root);
        criteriaQuery.where(predicate);
        setOrder(sortingParameters, criteriaQuery, root);

        TypedQuery<T> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(paginationParameters.getPageNumber() * paginationParameters.getPageSize());
        typedQuery.setMaxResults(paginationParameters.getPageSize());

        Pageable pageable = getPageable(paginationParameters, sortingParameters);

        long entityCount = getEntityCount(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, entityCount);
    }

    protected void addPredicates(Predicate predicate) {
        LOGGER.debug("addPredicates({})", predicate);
        if (CollectionUtils.isEmpty(predicates)) {
            predicates = new ArrayList<>();
        }
        predicates.add(predicate);
    }

    private Predicate getPredicate(List<FilteringParameters> filteringParameters,
                                   Root<T> entityRoot) {
        if (CollectionUtils.isEmpty(predicates)) {
            predicates = new ArrayList<>();
        }
        for (FilteringParameters parameter : filteringParameters) {
            if (parameter.getFilterBy() != null
                    && parameter.getFilterBy().fieldType.equalsIgnoreCase("string")) {
                predicates.add(
                        criteriaBuilder.like(entityRoot.get(parameter.getFilterBy().fieldName),
                                "%" + parameter.getFilterValue() + "%")
                );
            }
            if (parameter.getFilterBy() != null
                    && parameter.getFilterBy().fieldType.equalsIgnoreCase("integer")) {
                predicates.add(
                        criteriaBuilder.equal(entityRoot.get(parameter.getFilterBy().fieldName),
                                parameter.getFilterValue())
                );
            }
            if (parameter.getFilterBy() != null
                    && parameter.getFilterBy().fieldType.equalsIgnoreCase("decimal")) {
                predicates.add(
                        criteriaBuilder.or(
                                criteriaBuilder.equal(entityRoot.get(parameter.getFilterBy().fieldName),
                                        Double.parseDouble(parameter.getFilterValue())),
                                criteriaBuilder.and(
                                        criteriaBuilder.lt(entityRoot.get(parameter.getFilterBy().fieldName),
                                                Double.parseDouble(parameter.getFilterValue()) + 1),
                                        criteriaBuilder.gt(entityRoot.get(parameter.getFilterBy().fieldName),
                                                Double.parseDouble(parameter.getFilterValue())))));
            }

        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void setOrder(SortingParameters sortingParameters,
                          CriteriaQuery<T> criteriaQuery,
                          Root<T> entityRoot) {
        if (sortingParameters.getSortDirection().equals(Sort.Direction.ASC)) {
            criteriaQuery.orderBy(criteriaBuilder.asc(entityRoot.get(sortingParameters.getSortBy())),
                    criteriaBuilder.asc(entityRoot.get("createdDate")));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(entityRoot.get(sortingParameters.getSortBy())),
                    criteriaBuilder.desc(entityRoot.get("createdDate")));
        }
    }

    private Pageable getPageable(PaginationParameters paginationParameters, SortingParameters sortingParameters) {
        Sort sort = Sort.by(sortingParameters.getSortDirection(), sortingParameters.getSortBy());
        return PageRequest.of(paginationParameters.getPageNumber(), paginationParameters.getPageSize(), sort);
    }

    private long getEntityCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<T> countRoot = countQuery.from(basicClass);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }

    protected boolean isInvalidEntity(T entity) {
        LOGGER.debug("isInvalidEntity({})", entity);
        return Objects.isNull(entity);
    }

    //TODO all implementations of this method only check whether the ID is not null
    // this logic should be part of the isInvalidEntity method
    protected boolean isInvalidEntityId(T entity) {
        LOGGER.debug("isInvalidEntityId({})", entity);
        return false;
    }
}


