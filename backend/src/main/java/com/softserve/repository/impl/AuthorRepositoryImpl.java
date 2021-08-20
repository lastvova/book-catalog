package com.softserve.repository.impl;

import com.softserve.entity.Author;
import com.softserve.entity.Book;
import com.softserve.exception.DeleteAuthorWithBooksException;
import com.softserve.exception.WrongInputValueException;
import com.softserve.repository.AuthorRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

@Repository
public class AuthorRepositoryImpl extends BaseRepositoryImpl<Author, BigInteger> implements AuthorRepository {

    private static final Logger log = LoggerFactory.getLogger(AuthorRepositoryImpl.class);

    //    TODO not working
    public List<Author> getAuthorsByAverageRating() {
        log.debug("In getAuthorsByAverageRating of {}", basicClass.getName());
        return entityManager.createQuery("select a, avg (b.rating) as rating " +
                        "from Author a join AuthorBook ab on a.id = ab.author.id " +
                        "join Book b on ab.book.id = b.id " +
                        "group by a.id"
                , Author.class).getResultList();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Book> getBooksByAuthorId(BigInteger id) {
        log.debug("In getBookByAuthorId method with input value: [{}] of {}", id, basicClass.getName());
        if (Objects.isNull(id)) {
            throw new WrongInputValueException("Wrong id = " + id);
        }
        return entityManager.createQuery("select b from Book b " +
                        "join b.authors a " +
                        "where a.id = :authorId", Book.class)
                .setParameter("authorId", id)
                .getResultList();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public boolean hasBooks(BigInteger id) {
        log.debug("In hasBooks method with input value: [{}] of {}", id, basicClass.getName());
        if (Objects.isNull(id)) {
            throw new WrongInputValueException("Wrong id = " + id);
        }
        Long count = (Long) entityManager.createQuery("select count (b) from Book b " +
                        "join b.authors a " +
                        "where a.id = :id")
                .setParameter("id", id)
                .getSingleResult();
        return count > 0;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public boolean delete(BigInteger id) {
        log.debug("In delete method with input value: [{}] of {}", id, basicClass.getName());
        Author author = getById(id);
        if (Objects.isNull(author)) {
            return false;
        }
        if (hasBooks(id)) {
            throw new DeleteAuthorWithBooksException("Cant delete author with id: " + id);
        }
        entityManager.remove(author);
        return true;
    }

    @Override
    public boolean isInvalidEntity(Author author) {
        log.debug("In isInvalidEntity method with input value: [{}] of {}", author, basicClass.getName());
        return Objects.isNull(author) || StringUtils.isBlank(author.getFirstName());
    }
}
