package com.softserve.repository;

import com.softserve.entity.Book;

import java.math.BigInteger;
import java.util.List;

public interface BookRepository extends BaseRepository<Book, BigInteger> {

    boolean deleteBooks(List<Integer> ids);
}
