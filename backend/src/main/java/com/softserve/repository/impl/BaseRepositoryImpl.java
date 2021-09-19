package com.softserve.repository.impl;


import com.softserve.exception.WrongEntityException;
import com.softserve.repository.BaseRepository;
import com.softserve.util.FilteringParameters;
import com.softserve.util.PaginationParameters;
import com.softserve.util.SortingParameters;
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

    @SuppressWarnings("unchecked")
    protected BaseRepositoryImpl() {
        basicClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0];

    }

    @Override
    //TODO move transaction management to the service layer
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public T getById(I id) {
        LOGGER.debug("{}.getById({})", basicClass.getName(), id);
        //TODO should be part of validating methods
        if (Objects.isNull(id)) {
            //TODO inappropriate exception type
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
    //TODO you are already managing transactions at the service layer!!!
    //    @Override
    //    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    //    public Page<T> getAll(PaginationParameters paginationParameters, SortingParameters sortingParameters, FilteringParameters filteringParameters) {
    //        LOGGER.debug("{}.getAll()", this.getClass().getName());
    //        return baseRepository.getAll(paginationParameters, sortingParameters, filteringParameters);
    //    }
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<T> getAll(PaginationParameters paginationParameters, SortingParameters sortingParameters,
                          FilteringParameters filteringParameters) {
        LOGGER.debug("{}.getAll", basicClass.getName());
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

    private Predicate getPredicate(FilteringParameters filteringParameters,
                                   Root<T> entityRoot) {
        List<Predicate> predicates = new ArrayList<>();
        //TODO avoid using Objects.nonNull method within the application, replace all existing places with != operator
        // API Note:
        // This method exists to be used as a java.util.function.Predicate, filter(Objects::nonNull)
        if (Objects.nonNull(filteringParameters.getFilterBy())
                && filteringParameters.getFilterBy().fieldType.equalsIgnoreCase("string")) {
            predicates.add(
                    criteriaBuilder.like(entityRoot.get(filteringParameters.getFilterBy().fieldName),
                            "%" + filteringParameters.getFilterValue() + "%")
            );
        }
        if (Objects.nonNull(filteringParameters.getFilterBy())
                && filteringParameters.getFilterBy().fieldType.equalsIgnoreCase("integer")) {
            predicates.add(
                    criteriaBuilder.equal(entityRoot.get(filteringParameters.getFilterBy().fieldName),
                            filteringParameters.getFilterValue())
            );
        }
        if (Objects.nonNull(filteringParameters.getFilterBy())
                && filteringParameters.getFilterBy().fieldType.equalsIgnoreCase("decimal")) {
            predicates.add(
                    criteriaBuilder.or(
                            criteriaBuilder.equal(entityRoot.get(filteringParameters.getFilterBy().fieldName),
                                    Double.parseDouble(filteringParameters.getFilterValue())),
                            criteriaBuilder.and(
                                    criteriaBuilder.lt(entityRoot.get(filteringParameters.getFilterBy().fieldName),
                                            Double.parseDouble(filteringParameters.getFilterValue()) + 1),
                                    criteriaBuilder.gt(entityRoot.get(filteringParameters.getFilterBy().fieldName),
                                            Double.parseDouble(filteringParameters.getFilterValue())))));
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
        LOGGER.debug("{}.isInvalidEntity({})", basicClass.getName(), entity);
        return Objects.isNull(entity);
    }

    //TODO all implementations of this method only check whether the ID is not null
    // this logic should be part of the isInvalidEntity method
    protected boolean isInvalidEntityId(T entity) {
        LOGGER.debug("{}.isInvalidEntityId({})", basicClass.getName(), entity);
        return false;
    }
}


