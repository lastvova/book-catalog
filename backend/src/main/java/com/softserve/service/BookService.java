package com.softserve.service;

import com.softserve.entity.Book;

import java.math.BigInteger;
import java.util.List;

public interface BookService extends BaseService<Book, BigInteger> {

//    TODO
    List<Book> getBooksBySearchingParams(String searchBy ,String name);

}
