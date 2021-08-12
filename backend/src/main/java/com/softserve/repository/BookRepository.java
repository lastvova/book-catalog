package com.softserve.repository;

import com.softserve.entity.Author;
import com.softserve.entity.Book;

import java.math.BigInteger;
import java.util.List;

public interface BookRepository extends BasicRepository<Book, BigInteger> {

    List<Book> getAllAvailableBooks(Integer firstResult, Integer maxResult, String sort);

    List<Book> getBooksByName(String name, String sort);

    List<Book> getAllBooksByAuthor(Author author);

    List<Book> getAllBooksByRating(int rating);

    void deleteBooksWithReviews(List<BigInteger> ids);
}
