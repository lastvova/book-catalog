package com.softserve.repository;

import com.softserve.entity.Book;

import java.math.BigInteger;
import java.util.List;

public interface BookRepository extends BaseRepository<Book, BigInteger> {

    List<Book> getBooksByName(String name);

    List<Book> getBooksByRating(int rating);

    Book findByIsbn(BigInteger isbn);

}
