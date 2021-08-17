package com.softserve.service.impl;

import com.softserve.entity.Book;
import com.softserve.exception.EntityNotFoundException;
import com.softserve.repository.BookRepository;
import com.softserve.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

@Slf4j
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository repository;

    @Autowired
    public BookServiceImpl(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Book findById(BigInteger id) {
        return repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Not found book with id = " + id));
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Book> getAll() {
        return repository.getAll();
    }

    @Override
    @Transactional
    public Book save(Book entity) {
        return repository.save(entity);
    }

    @Override
    @Transactional
    public Book update(Book entity) {
        return repository.update(entity);
    }

    @Override
    @Transactional
    public Book delete(BigInteger id) {
        Book book = findById(id);
        return repository.delete(book);
    }

    @Override
    public Book getBooksWithReviews(BigInteger id) {
        return null;
    }
}
