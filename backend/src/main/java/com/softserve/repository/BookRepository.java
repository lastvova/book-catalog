package com.softserve.repository;

import com.softserve.entity.Author;
import com.softserve.entity.Book;

import java.math.BigInteger;
import java.util.List;

public interface BookRepository extends BasicRepository<Book, BigInteger> {

    List<Book> getAllAvailableBooks();

    List<Book> getAllBooksByAuthor(Author author);

    List<Book> getAllBooksByRating(int rating);

    void deleteBookWithReviews(BigInteger id);

    void deleteBooksWithReviews(List<BigInteger> ids);
}
