package com.softserve.repository.impl;

import com.softserve.entity.Author;
import com.softserve.entity.Book;
import com.softserve.exception.EntityNotFoundException;
import com.softserve.repository.BookRepository;
import com.softserve.utils.BookFilterParameters;
import com.softserve.utils.ListParams;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepositoryImpl extends BaseRepositoryImpl<Book, BigInteger> implements BookRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookRepositoryImpl.class);

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Book getById(BigInteger id) {
        LOGGER.debug("getById({})", id);
        if (id == null) {
            throw new IllegalStateException("Wrong book id");
        }
        Book book = entityManager.find(Book.class, id);
        if (book == null) {
            throw new EntityNotFoundException("Not found entity with id: " + id.toString());
        }
        book.getAuthors().size();
        return book;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<Book> getAll(ListParams<?> params) {
        LOGGER.debug("getAll");
        Page<Book> books = super.getAll(params);
        books.getContent().forEach(book -> book.getAuthors().size());
        return books;
    }

    //TODO
    @Override
    public boolean deleteBooks(List<Integer> ids) {
        LOGGER.debug("deleteBooks({})", ids);
        return false;
    }

    @Override
    protected boolean isInvalidEntity(Book book) {
        LOGGER.debug("isInvalidEntity({})", book);

        return super.isInvalidEntity(book) || StringUtils.isBlank(book.getName())
                || book.getIsbn() == null
                || CollectionUtils.isEmpty(book.getAuthors())
                || isInValidYearOfPublisher(book);
    }

    @Override
    protected boolean isInvalidEntityId(Book book) {
        LOGGER.debug("isInvalidEntityId({})", book);
        return book.getId() == null;
    }

    private boolean isInValidYearOfPublisher(Book book) {
        if (book.getYearPublisher() == null) {
            return false;
        }
        return book.getYearPublisher() < 0
                || book.getYearPublisher() > LocalDate.now().getYear();
    }

    @Override
    public Predicate getPredicate(ListParams<?> params, Root<Book> books) {
        List<Predicate> predicates = new ArrayList<>();
        if (params.getPattern() != null) {
            BookFilterParameters filterParameters = (BookFilterParameters) params.getPattern();
            if (filterParameters.getName() != null) {
                predicates.add(
                        criteriaBuilder.like(books.get("name"),
                                "%" + filterParameters.getName() + "%")
                );
            }
            if (filterParameters.getYearPublisher() != null) {
                predicates.add(
                        criteriaBuilder.equal(books.get("yearPublisher"), filterParameters.getYearPublisher()));
            }
            if (filterParameters.getToRating() != null && filterParameters.getFromRating() != null) {
                predicates.add(
                        criteriaBuilder.between(books.get("rating"),
                                filterParameters.getFromRating(), filterParameters.getToRating()));
            }
            if (filterParameters.getIsbn() != null) {
                predicates.add(
                        criteriaBuilder.equal(books.get("isbn"), filterParameters.getIsbn())
                );
            }
            if (filterParameters.getPublisher() != null) {
                predicates.add(
                        criteriaBuilder.like(books.get("publisher"),
                                "%" + filterParameters.getPublisher() + "%")
                );
            }
            if (filterParameters.getAuthorNameAndSecondName() != null) {
                Join<Book, Author> authors = books.join("authors");
                predicates.add(
                        criteriaBuilder.or(
                                criteriaBuilder.like(authors.get("firstName"),
                                        "%" + filterParameters.getAuthorNameAndSecondName() + "%"),
                                criteriaBuilder.like(authors.get("secondName"),
                                        "%" + filterParameters.getAuthorNameAndSecondName() + "%"))
                );
            }
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    //This overriding needs for correct count books with joining authors
    @Override
    protected long getEntityCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Book> books = countQuery.from(Book.class);
        books.join("authors");
        countQuery.select(criteriaBuilder.count(books)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
