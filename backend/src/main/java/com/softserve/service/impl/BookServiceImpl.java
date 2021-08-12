package com.softserve.service.impl;

import com.softserve.entity.Book;
import com.softserve.repository.BookRepository;
import com.softserve.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Book findById(BigInteger id) {
        return repository.findById(id).get();
    }

    @Override
    public List<Book> getAll() {
        return repository.getAll();
    }

    @Override
    public Book save(Book entity) {
        return repository.save(entity);
    }

    @Override
    public Book update(Book entity) {
        return repository.update(entity);
    }

    @Override
    public Book delete(BigInteger id) {
        return repository.delete(id);
    }
}
