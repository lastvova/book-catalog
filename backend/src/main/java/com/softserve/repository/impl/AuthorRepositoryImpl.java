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
        return entityManager.createQuery("select a from Author a", Author.class)
                .getResultList();
    }

    @Override
    public List<Author> getAuthorsByAverageRating() {
        return entityManager.createQuery("select a, avg (b.rating) as rating " +
                "from Author a join AuthorBook ab on a.id = ab.author.id " +
                        "join Book b on ab.book.id = b.id " +
                        "group by a.id"
                ,Author.class).getResultList();
    }

    @Override
    public void deleteAuthorIfItDoesNotHaveBooks(BigInteger id) {

    }

    @Override
    public void deleteAuthorsIfTheyDoNotHaveBooks(List<BigInteger> ids) {

    }
}
