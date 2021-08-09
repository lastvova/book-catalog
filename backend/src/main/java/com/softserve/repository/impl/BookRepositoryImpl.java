package com.softserve.repository.impl;

import com.softserve.entity.Author;
import com.softserve.entity.Book;
import com.softserve.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Slf4j
@Repository()
public class BookRepositoryImpl extends BasicRepositoryImpl<Book, BigInteger> implements BookRepository {

    @Override
    public List<Book> getAllAvailableBooks() {
        return null;
    }

    @Override
    public List<Book> getAllBooksByAuthor(Author author) {
        return null;
    }

    @Override
    public List<Book> getAllBooksByRating(int rating) {
        return null;
    }

    @Override
    public void deleteBookWithReviews(BigInteger id) {

    }

    @Override
    public void deleteBooksWithReviews(List<BigInteger> ids) {

    }
}
