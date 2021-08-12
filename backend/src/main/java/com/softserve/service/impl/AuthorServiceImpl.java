package com.softserve.service.impl;

import com.softserve.entity.Author;
import com.softserve.repository.AuthorRepository;
import com.softserve.service.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Slf4j
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository repository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository repository) {
        this.repository = repository;
    }

    @Override
    public Author findById(BigInteger id) {
        return repository.findById(id).get();
    }

    @Override
    public List<Author> getAll() {
        return repository.getAll();
    }

    @Override
    public Author save(Author entity) {
        return repository.save(entity);
    }

    @Override
    public Author update(Author entity) {
        return repository.update(entity);
    }

    @Override
    public Author delete(BigInteger id) {
        return repository.delete(id);
    }
}
