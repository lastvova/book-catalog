package com.softserve.repository.impl;

import com.softserve.entity.Book;
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

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Repository
public class BookRepositoryImpl extends BaseRepositoryImpl<Book, BigInteger> implements BookRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookRepositoryImpl.class);

    @Override
    public Book getById(BigInteger id) {
        LOGGER.debug("getById({})", id);
        if (Objects.isNull(id)) {
            throw new IllegalStateException("Wrong book id");
        }
        Book book = entityManager.find(Book.class, id);
        if (Objects.isNull(book)) {
            throw new EntityNotFoundException("Not found entity with id: " + id.toString());
        }
        book.getAuthors().size();
        return book;
    }

    @Override
    public Page<Book> getAll(PaginationParameters paginationParameters, SortingParameters sortingParameters,
                             FilteringParameters filteringParameters) {
        LOGGER.debug("getAll");
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
                || Objects.isNull(book.getIsbn())
                || CollectionUtils.isEmpty(book.getAuthors())
                || isInValidYearOfPublisher(book);
    }

    @Override
    protected boolean isInvalidEntityId(Book book) {
        LOGGER.debug("isInvalidEntityId({})", book);
        return Objects.isNull(book.getId());
    }

    private boolean isInValidYearOfPublisher(Book book) {
        if (Objects.isNull(book.getYearPublisher())) {
            return false;
        }
        return book.getYearPublisher() < 0
                || book.getYearPublisher() > LocalDate.now().getYear();
    }
}
