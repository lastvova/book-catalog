package com.softserve.service.impl;

import com.softserve.entity.Book;
import com.softserve.repository.BookRepository;
import com.softserve.service.BookService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

@Service
public class BookServiceImpl extends BaseServiceImpl<Book, BigInteger> implements BookService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookServiceImpl.class);
    private final BookRepository repository;

    @Autowired
    public BookServiceImpl(BookRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public boolean isInvalidEntity(Book book) {
        LOGGER.debug("isInvalidEntity({})", book);
        return super.isInvalidEntity(book) || StringUtils.isBlank(book.getName())
                || book.getIsbn() == null
                || CollectionUtils.isEmpty(book.getAuthors())
                || isInValidYearOfPublisher(book);
    }

    @Override
    @Transactional
    public boolean bulkDelete(List<BigInteger> ids) {
        LOGGER.debug("bulkDelete({})", ids);
        return repository.deleteBooks(ids);
    }

    private boolean isInValidYearOfPublisher(Book book) { // todo: duplicate of: com.softserve.repository.impl.BookRepositoryImpl.isInValidYearOfPublisher
        if (book.getYearPublisher() == null) {
            return false;
        }
        return book.getYearPublisher() < 0
                || book.getYearPublisher() > LocalDate.now().getYear();
    }
}
