package com.softserve.service;

import com.softserve.entity.Author;

import java.math.BigInteger;
import java.util.List;

public interface AuthorService extends BaseService<Author, BigInteger> {

    public boolean bulkDelete(List<BigInteger> ids);
}
