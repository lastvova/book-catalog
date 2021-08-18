package com.softserve.repository.impl;

import com.softserve.entity.Author;
import com.softserve.entity.Book;
import com.softserve.repository.AuthorRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

@Repository
public class AuthorRepositoryImpl extends BaseRepositoryImpl<Author, BigInteger> implements AuthorRepository {

    private static final Logger log = LoggerFactory.getLogger(AuthorRepositoryImpl.class);

    public List<Author> getAuthorsByAverageRating() {
        log.debug("In getAuthorsByAverageRating of {}", basicClass.getName());
        return entityManager.createQuery("select a, avg (b.rating) as rating " +
                        "from Author a join AuthorBook ab on a.id = ab.author.id " +
                        "join Book b on ab.book.id = b.id " +
                        "group by a.id"
                , Author.class).getResultList();
    }

    //    TODO not work
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Book> getBooksByAuthorId(BigInteger id) {
        log.debug("In getBooksByAuthorId of {}", basicClass.getName());
        return entityManager.createQuery("select b from Book b " +
                        "left join AuthorBook ab on ab.book.id = b.id " +
                        "where ab.author.id = :authorId", Book.class)
                .setParameter("authorId", id)
                .getResultList();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public boolean hasBooks(BigInteger id) {
        log.debug("In hasBooks of {}", basicClass.getName());
        Long count = (Long) entityManager.createQuery("select count (b.id) from Book b " +
                        "left join AuthorBook ab on b.id = ab.book.id " +
                        "where ab.author.id = :id")
                .setParameter("id", id)
                .getSingleResult();
        return count > 0;
    }

    @Override
    public boolean isValidEntity(Author author) {
        log.debug("In isValidEntity of {}", basicClass.getName());
        return !StringUtils.isBlank(author.getFirstName());
    }
}
