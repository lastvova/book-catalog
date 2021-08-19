package com.softserve.repository;

import com.softserve.entity.Author;
import com.softserve.entity.Book;

import java.math.BigInteger;
import java.util.List;

public interface AuthorRepository extends BaseRepository<Author, BigInteger> {

    List<Book> getBooksByAuthorId(BigInteger id);

    boolean hasBooks(BigInteger id);
}
