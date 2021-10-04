package com.softserve.repository;

import com.softserve.entity.Author;

import java.math.BigInteger;
import java.util.List;

public interface AuthorRepository extends BaseRepository<Author, BigInteger> {

    boolean hasBooks(BigInteger id);

    boolean deleteAuthors(List<BigInteger> ids);
}
