package com.softserve.repository;

import com.softserve.entity.Author;

import java.math.BigInteger;
import java.util.List;

public interface AuthorRepository extends BasicRepository<Author, BigInteger> {

    List<Author> getAllAvailableAuthors();

    List<Author> getAuthorsByAverageRating();

    void deleteAuthorIfItDoesNotHaveBooks(BigInteger id);

    void deleteAuthorsIfTheyDoNotHaveBooks(List<BigInteger> ids);

}
