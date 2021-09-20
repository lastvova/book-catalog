package com.softserve.repository.impl;

import com.softserve.entity.Author;
import com.softserve.exception.DeleteAuthorWithBooksException;
import com.softserve.repository.AuthorRepository;
import com.softserve.utils.AuthorFilterParameters;
import com.softserve.utils.ListParams;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AuthorRepositoryImpl extends BaseRepositoryImpl<Author, BigInteger> implements AuthorRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorRepositoryImpl.class);

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public boolean hasBooks(BigInteger id) {
        LOGGER.debug("hasBooks({})", id);
        if (id == null) {
            throw new IllegalStateException("Wrong author id");
        }
        BigInteger count = (BigInteger) entityManager
                .createNativeQuery("select exists (select 1 from authors_books a " +
                        "where a.author_id = :id)")
                .setParameter("id", id)
                .getSingleResult();
        return count.compareTo(BigInteger.ZERO) > 0;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public boolean delete(BigInteger id) {
        LOGGER.debug("delete({})", id);
        if (id == null) {
            throw new IllegalArgumentException("Wrong author id");
        }
        Author author = getById(id);
        if (author == null) {
            return false;
        }
        if (hasBooks(id)) {
            throw new DeleteAuthorWithBooksException("Cant delete author, because it has books");
        }
        entityManager.remove(author);
        return true;
    }

    @Override
    public Predicate getPredicate(ListParams<?> params, Root<Author> authors) {
        List<Predicate> predicates = new ArrayList<>();
        if (params.getPattern() != null) {
            AuthorFilterParameters filterParameters = (AuthorFilterParameters) params.getPattern();
            if (filterParameters.getFirstName() != null) {
                predicates.add(
                        criteriaBuilder.like(authors.get("firstName"),
                                "%" + filterParameters.getFirstName() + "%")
                );
            }
            if (filterParameters.getSecondName() != null) {
                predicates.add(
                        criteriaBuilder.like(authors.get("secondName"),
                                "%" + filterParameters.getSecondName() + "%"));
            }
            if (filterParameters.getToRating() != null && filterParameters.getFromRating() != null) {
                predicates.add(
                        criteriaBuilder.between(authors.get("rating"),
                                filterParameters.getFromRating(), filterParameters.getToRating()));
            }
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    @Override
    public boolean deleteAuthors(List<Integer> ids) { //TODO
        LOGGER.debug("deleteAuthors({})", ids);
        return false;
    }

    @Override
    public boolean isInvalidEntity(Author author) {
        LOGGER.debug("isInvalidEntity({})", author);
        return super.isInvalidEntity(author) || StringUtils.isBlank(author.getFirstName());
    }

    @Override
    protected boolean isInvalidEntityId(Author author) {
        LOGGER.debug("isInvalidEntityId({})", author);
        return author.getId() == null;
    }
}
