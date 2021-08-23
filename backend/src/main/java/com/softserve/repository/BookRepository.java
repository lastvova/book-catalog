package com.softserve.repository;

import com.softserve.entity.Book;

import java.math.BigInteger;
import java.util.List;

// todo: where method for batch remove?
// todo: redundant methods: getBooksByName, getBooksByRating, findByIsbn
public interface BookRepository extends BaseRepository<Book, BigInteger> {

    List<Book> getBooksByName(String name);

    List<Book> getBooksByPublisher(String name);

    List<Book> getBooksByAuthorsName(String name);

    List<Book> getBooksByAuthorsSecondName(String name);

    List<Book> getBooksByRating(Integer rating);

    Book getByIsbn(BigInteger isbn);

}
