package com.softserve.service;

import com.softserve.entity.Author;
import com.softserve.entity.Book;

import java.math.BigInteger;
import java.util.List;

public interface AuthorService extends BaseService<Author, BigInteger> {
    //TODO
    public List<Book> getBooksByAuthorId(BigInteger id);
}
