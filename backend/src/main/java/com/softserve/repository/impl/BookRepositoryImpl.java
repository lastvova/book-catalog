package com.softserve.repository.impl;

import com.softserve.entity.Author;
import com.softserve.entity.Book;
import com.softserve.enums.EntityFields;
import com.softserve.exception.EntityNotFoundException;
import com.softserve.repository.BookRepository;
import com.softserve.util.FilteringParameters;
import com.softserve.util.PaginationParameters;
import com.softserve.util.SortingParameters;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
    public Page<Book> getAll(PaginationParameters paginationParameters, SortingParameters sortingParameters,
                             List<FilteringParameters> filteringParameters) {
        LOGGER.debug("getAll");
        Optional<FilteringParameters> parameters = filteringParameters.stream()
                .filter(p -> p.getFilterBy().equals(EntityFields.BOOK_AUTHOR))
                .findFirst();
        if (parameters.isPresent()) {
            filteringParameters.remove(parameters.get());
            addPredicateFotFilteringByAuthor(parameters.get());
        }
        Page<Book> books = super.getAll(paginationParameters, sortingParameters, filteringParameters);
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

    private void addPredicateFotFilteringByAuthor(FilteringParameters filteringParameters) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> query = criteriaBuilder.createQuery(Book.class);
        Root<Author> authors = query.from(Author.class);
        super.addPredicates(criteriaBuilder.or(
                criteriaBuilder.like(authors.get(EntityFields.AUTHOR_FIRST_NAME.fieldName),
                        "%" + filteringParameters.getFilterValue() + "%"),
                criteriaBuilder.like(authors.get(EntityFields.AUTHOR_SECOND_NAME.fieldName),
                        "%" + filteringParameters.getFilterValue() + "%")));
    }
}
