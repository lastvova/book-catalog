package com.softserve.repository.impl;

import com.softserve.entity.Author;
import com.softserve.repository.AuthorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Slf4j
@Repository
public class AuthorRepositoryImpl extends BasicRepositoryImpl<Author, BigInteger> implements AuthorRepository {
    @Override
    public List<Author> getAllAvailableAuthors() {
        return null;
    }

    @Override
    public List<Author> getAuthorsByAverageRating() {
        return null;
    }

    @Override
    public void deleteAuthorIfItDoesNotHaveBooks(BigInteger id) {

    }

    @Override
    public void deleteAuthorsIfTheyDoNotHaveBooks(List<BigInteger> ids) {

    }
}
