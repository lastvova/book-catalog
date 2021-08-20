package com.softserve.service.impl;

import com.softserve.entity.Book;
import com.softserve.exception.WrongInputValueException;
import com.softserve.repository.BookRepository;
import com.softserve.service.BookService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class BookServiceImpl extends BaseServiceImpl<Book, BigInteger> implements BookService {

    private static final Logger log = LoggerFactory.getLogger(BookServiceImpl.class);
    private final BookRepository repository;

    @Autowired
    public BookServiceImpl(BookRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Book> getBooksByName(String name) {
        log.debug("Enter into getBooksByName method with input value: [{}]", name);
        if (StringUtils.isBlank(name)) {
            throw new WrongInputValueException("Wrong input string for search");
        }
        return repository.getBooksByName(name);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Book> getBooksByRating(Integer rating) {
        log.debug("Enter into getBooksByRating method with input value: [{}]", rating);
        if (Objects.isNull(rating) || rating > 5 || rating <= 0) {
            throw new WrongInputValueException("Wrong input rating");
        }
        return repository.getBooksByRating(rating);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Book getBookWithReviews(BigInteger id) {
        log.debug("Enter into getBooksWithReviews method with input value: [{}]", id);
        return null;
    }

    @Override
    public boolean isInvalidEntity(Book book) {
        log.debug("Enter into isInvalidEntity method with input value: [{}]", book);
        return Objects.isNull(book) || StringUtils.isBlank(book.getName())
                || Objects.isNull(book.getIsbn())
                || CollectionUtils.isEmpty(book.getAuthors())
                || book.getYearPublisher() < 0
                || book.getYearPublisher() > LocalDate.now().getYear();
    }
}
