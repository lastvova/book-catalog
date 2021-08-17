package com.softserve.service;

import com.softserve.entity.Book;

import java.math.BigInteger;

public interface BookService extends BasicService<Book, BigInteger> {

    Book getBooksWithReviews(BigInteger id);
}
