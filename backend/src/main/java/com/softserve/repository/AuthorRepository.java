package com.softserve.repository;

import com.softserve.entity.Author;
import com.softserve.entity.Book;

import java.math.BigInteger;
import java.util.List;

public interface AuthorRepository extends BasicRepository<Author, BigInteger> {

    List<Book> getBooksByAuthorId(BigInteger id);

    public boolean hasBooks(BigInteger id);
}
