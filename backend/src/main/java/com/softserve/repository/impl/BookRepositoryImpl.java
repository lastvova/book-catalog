package com.softserve.repository.impl;

import com.softserve.entity.Author;
import com.softserve.entity.Book;
import com.softserve.exception.WrongEntityException;
import com.softserve.exception.WrongInputValueException;
import com.softserve.repository.BookRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class BookRepositoryImpl extends BaseRepositoryImpl<Book, BigInteger> implements BookRepository {

    private static final Logger log = LoggerFactory.getLogger(BookRepositoryImpl.class); // todo: wrong name pattern!

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Book> getBooksByName(String name) {
        log.debug("In getBooksByName method with input value: [{}] of {}", name, basicClass.getName());
        if (StringUtils.isBlank(name)) {
            throw new WrongInputValueException("Wrong input string for search");
        }
        return entityManager
                .createQuery("select b from Book b " +
                        "join fetch b.authors a " +
                        "where b.name like :name ", Book.class)
                .setParameter("name", "%" + name + "%")
                .getResultList();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Book> getBooksByPublisher(String name) {
        log.debug("In getBooksByPublisher method with input value: [{}] of {}", name, basicClass.getName());
        if (StringUtils.isBlank(name)) {
            throw new WrongInputValueException("Wrong input string for search");
        }
        return entityManager
                .createQuery("select b from Book b " +
                        "join fetch b.authors a " +
                        "where b.publisher like :name ", Book.class)
                .setParameter("name", "%" + name + "%")
                .getResultList();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Book> getBooksByAuthorsName(String name) {
        log.debug("In getBooksByAuthorsName method with input value: [{}] of {}", name, basicClass.getName());
        if (StringUtils.isBlank(name)) {
            throw new WrongInputValueException("Wrong input string for search");
        }
        return entityManager
                .createQuery("select b from Book b " +
                        "join fetch b.authors a " +
                        "where a.firstName like :name ", Book.class)
                .setParameter("name", "%" + name + "%")
                .getResultList();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Book> getBooksByAuthorsSecondName(String name) {
        log.debug("In getBooksByAuthorsSecondName method with input value: [{}] of {}", name, basicClass.getName());
        if (StringUtils.isBlank(name)) {
            throw new WrongInputValueException("Wrong input string for search");
        }
        return entityManager
                .createQuery("select b from Book b " +
                        "join fetch b.authors a " +
                        "where a.secondName like :name ", Book.class)
                .setParameter("name", "%" + name + "%")
                .getResultList();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Book> getBooksByRating(Integer rating) {
        log.debug("In getBooksByRating method with input value: [{}] of {}", rating, basicClass.getName());
        if (Objects.isNull(rating) || rating <= 0 || rating > 5) {
            throw new WrongInputValueException("Wrong input rating");
        }
        return entityManager
                .createQuery("select b from Book b " +
                        "where b.rating >= :rating and b.rating < :rating+1 ", Book.class)
                .setParameter("rating", BigDecimal.valueOf(rating))
                .getResultList();
    }

    // todo: redundant method!!!
    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Book save(Book book) {
        log.debug("In save method with input value: [{}] of {}", book, basicClass.getName());
        if (isInvalidEntity(book)) {
            throw new WrongEntityException("Wrong book in save method");
        }
        if (!Objects.isNull(getByIsbn(book.getIsbn()))) {
            throw new WrongInputValueException("This isbn already exist: " + book.getIsbn());
        }
        entityManager.persist(book);
        return book;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    // todo: redundant method!!!
    public Book update(Book book) {
        log.debug("In update method with input value: [{}] of {}", book, basicClass.getName());
        if (isInvalidEntity(book)) {
            throw new WrongEntityException("Wrong book in update method");
        }
        Book temporaryBook = getByIsbn(book.getIsbn());
        if (!Objects.isNull(temporaryBook) && !Objects.equals(temporaryBook.getId(), book.getId())) {
            throw new WrongInputValueException("This isbn already exist: " + book.getIsbn());
        }
        // todo: author ID must be in this method without this manipulation
        book.setAuthors(book.getAuthors().stream()
                .map(author -> entityManager.getReference(Author.class, author.getId()))
                .collect(Collectors.toSet()));
        return entityManager.merge(book);
    }

    //    try catch here, because getSingleResult throws NoResultException if not founded entity
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    // todo: redundant method!!!
    public Book getByIsbn(BigInteger isbn) {
        log.debug("In findByIsbn method with input value: [{}] of {}", isbn, basicClass.getName());
        if (Objects.isNull(isbn)) {
            throw new WrongInputValueException("Wrong isbn");
        }
        Book book;
        try {
            book = entityManager.createQuery("select b from Book b where b.isbn = :isbn", Book.class)
                    .setParameter("isbn", isbn)
                    .getSingleResult();
        } catch (NoResultException e) {
            log.info("Didn't find book with provided isbn: {}", isbn);
            return null;
        }
        return book;
    }

    @Override
    public Book getById(BigInteger id) {
        log.debug("In getBookWithReviewsAndAuthors method with input value: [{}] of {}", id, basicClass.getName());
        if (Objects.isNull(id)) {
            throw new WrongInputValueException("Wrong id = " + id);
        }
        // todo: how authors will appear in your Book entity?
        return entityManager.createQuery("select b from Book b " +
                        "join fetch b.authors " +
                        "where b.id = :id", Book.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public List<Book> getAll() {
        log.debug("In getAll method of {}", basicClass.getName());
        return entityManager.createQuery("select b from Book b " +
                        "join fetch b.authors", Book.class)
                .getResultList();
    }

    @Override
    protected boolean isInvalidEntity(Book book) {
        log.debug("In isInvalidEntity method with input value: [{}] of {}", book, basicClass.getName());
        return super.isInvalidEntity(book) || StringUtils.isBlank(book.getName())
                || Objects.isNull(book.getIsbn())
                || CollectionUtils.isEmpty(book.getAuthors())
                || book.getYearPublisher() < 0
                || book.getYearPublisher() > LocalDate.now().getYear();
    }
}
