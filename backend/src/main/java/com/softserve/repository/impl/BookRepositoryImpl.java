package com.softserve.repository.impl;

import com.softserve.entity.Author;
import com.softserve.entity.Book;
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

import javax.persistence.criteria.CriteriaDelete;
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
        Book book = super.getById(id);
        if (book != null) {
            book.getAuthors().size();
        }
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

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public boolean deleteBooks(List<BigInteger> ids) {
        LOGGER.debug("deleteBooks({})", ids);
        if (CollectionUtils.isEmpty(ids)) {
            throw new IllegalArgumentException("Wrong ids in bulk delete");
        }
        CriteriaDelete<Book> deleteQuery = criteriaBuilder.createCriteriaDelete(Book.class);
        Root<Book> bookAuthors = deleteQuery.from(Book.class);
        deleteQuery.where(bookAuthors.get("id").in(ids));
        return entityManager.createQuery(deleteQuery).executeUpdate() > 0; // todo: better to change method signature to return count of removed books
    }

    @Override
    protected boolean isInvalidEntity(Book book) {
        LOGGER.debug("isInvalidEntity({})", book);

        return super.isInvalidEntity(book) || StringUtils.isBlank(book.getName())
                || isInValidIsbn(book.getIsbn())
                || CollectionUtils.isEmpty(book.getAuthors())
                || isInValidYearOfPublisher(book.getYearPublisher());
    }

    private boolean isInValidIsbn(BigInteger isbn) {
        return isbn == null || isbn.compareTo(BigInteger.valueOf(999_999_999_999L)) < 0;
    }

    private boolean isInValidYearOfPublisher(Integer year) {
        if (year == null) {
            return false;
        }
        return year < 0 || year > LocalDate.now().getYear();
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
            if (filterParameters.getFromRating() != null) {
                predicates.add(
                        criteriaBuilder.ge(books.get("rating"), filterParameters.getFromRating()));
            }
            if (filterParameters.getToRating() != null) {
                predicates.add(
                        criteriaBuilder.lt(books.get("rating"), filterParameters.getToRating()));
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
            if (filterParameters.getSearchingName() != null) {
                String filterName = filterParameters.getSearchingName();
                Join<Book, Author> authors = books.join("authors");
                predicates.add(
                        criteriaBuilder.or(
                                criteriaBuilder.like(authors.get("firstName"),
                                        "%" + filterName + "%"),
                                criteriaBuilder.like(authors.get("secondName"),
                                        "%" + filterName + "%"))
                );
            }
            if (filterParameters.getAuthorId() != null) {
                BigInteger authorId = filterParameters.getAuthorId();
                Join<Book, Author> authors = books.join("authors");
                predicates.add(
                        criteriaBuilder.equal(authors.get("id"), authorId));
            }
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    //This overriding needs for correct count books with joining authors
    // Type Long was used, because methods "count" and "countDistinct" returning type Long
    // CountDistinct was used, because if to use count with groupBy(book.id), the calculation of count all books will be incorrect.
    // In this case, the number of books for each author will be calculated
    @Override
    protected long getEntityCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Book> books = countQuery.from(Book.class);
        books.join("authors");
        countQuery.select(criteriaBuilder.countDistinct(books)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
