package com.softserve.repository.impl;

import com.softserve.entity.Author;
import com.softserve.entity.Book;
import com.softserve.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Repository
public class BookRepositoryImpl extends BasicRepositoryImpl<Book, BigInteger> implements BookRepository {

    @Override
    public List<Book> getAllAvailableBooks(Integer firstResult, Integer maxResult, String sort) {
        log.info("In getAllAvailableBooksWithRating of BookRepositoryImpl");
        return entityManager.createQuery("select b from Book b order by b.rating " + sort + ", b.createDate " + sort, Book.class)
                .setFirstResult(firstResult)
                .setMaxResults(maxResult)
                .getResultList();
    }

    @Override
    public List<Book> getAllBooksByAuthor(Author author) {
        log.info("In getAllByAuthor of BookRepositoryImpl");
        return entityManager
                .createQuery("select b from Book b where b.id in " +
                        "(select a.book.id from AuthorBook a where a.author.id = :authorId)", Book.class)
                .setParameter("authorId", author.getId())
                .getResultList();
    }

    @Override
    public List<Book> getBooksByName(String name, String sort) {
        log.info("In getBooksByName of BookRepositoryImpl");
        return entityManager
                .createQuery("select b from Book b " +
                        "where b.name like ?1 order by b.rating " + sort + ",b.createDate " + sort, Book.class)
                .setParameter(1, "%" + name + "%")
                .getResultList();
    }

    @Override
    public List<Book> getAllBooksByRating(int rating) {
        log.info("In getAllBooksByRating of BookRepositoryImpl");
        return entityManager
                .createQuery("select b from Book b " +
                        "where b.rating between :rating and :rating+1 ", Book.class)
                .setParameter("rating", BigDecimal.valueOf(rating))
                .getResultList();
    }

    @Override
    public void deleteBooksWithReviews(List<BigInteger> ids) {
        log.info("In deleteBooksWithReviews of BookRepositoryImpl");
        entityManager
                .createQuery("delete from Book b where b.id in (:ids)")
                .setParameter("ids", ids)
                .executeUpdate();
    }

    //    TODO save with author
    @Override
    public Book save(Book book) {
        book.getAuthors()
                .stream()
                .iterator()
                .forEachRemaining(author -> entityManager.getReference(Author.class, author.getId()));

        entityManager.persist(book);
        return book;
    }
}
