package com.softserve.repository.impl;

import com.softserve.entity.Author;
import com.softserve.entity.Book;
import com.softserve.repository.AuthorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Slf4j
@Repository
public class AuthorRepositoryImpl extends BasicRepositoryImpl<Author, BigInteger> implements AuthorRepository {
    public List<Author> getAuthorsByAverageRating() {
        return entityManager.createQuery("select a, avg (b.rating) as rating " +
                        "from Author a join AuthorBook ab on a.id = ab.author.id " +
                        "join Book b on ab.book.id = b.id " +
                        "group by a.id"
                , Author.class).getResultList();
    }

    @Override
    public List<Book> getBooksByAuthorId(BigInteger id) {
        return entityManager.createQuery("select b from Book b " +
                        "left join AuthorBook ab on ab.book.id = b.id " +
                        "where ab.author.id = :authorId", Book.class)
                .setParameter("authorId", id)
                .getResultList();
    }

    @Override
    public boolean hasBooks(BigInteger id) {
        Long count = (Long) entityManager.createQuery("select count (b.id) from Book b " +
                        "left join AuthorBook ab on b.id = ab.book.id " +
                        "where ab.author.id = :id")
                .setParameter("id", id)
                .getSingleResult();
        return count > 0;
    }
}
