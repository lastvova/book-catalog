package com.softserve.service;

import com.softserve.entity.Book;

import java.math.BigInteger;
import java.util.List;

public interface BookService extends BaseService<Book, BigInteger> {

    Book getBookWithReviews(BigInteger id);

    List<Book> getBooksByName(String name);

    List<Book> getBooksByRating(Integer rating);
}
