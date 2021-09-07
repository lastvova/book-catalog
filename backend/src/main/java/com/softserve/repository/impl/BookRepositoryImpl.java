package com.softserve.repository.impl;

import com.softserve.entity.Book;
import com.softserve.exception.EntityNotFoundException;
import com.softserve.repository.BookRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Repository
public class BookRepositoryImpl extends BaseRepositoryImpl<Book, BigInteger> implements BookRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookRepositoryImpl.class);
    private static final String GET_ALL_BOOKS = "select b.*,\n" +
            "       (select ifnull(round(avg(r.rating),2),0) from reviews r where r.book_id = b.id) as rating,\n" +
            "       a.*\n" +
            "from books b\n" +
            "         left join authors_books ab on b.id = ab.book_id\n" +
            "         left join authors a on ab.author_id = a.id " +
            "group by b.id;";

    @Override
    public Book getById(BigInteger id) {
        LOGGER.debug("{}.getById({})", basicClass.getName(), id);
        if (Objects.isNull(id)) {
            throw new IllegalStateException("Wrong book id");
        }
        Book book = entityManager.find(Book.class, id);
        if (Objects.isNull(book)) {
            throw new EntityNotFoundException("Not found entity with id: " + id.toString());
        }
        book.getAuthors().size();
        return book;
    }

    @Override
    public List<Book> getAll() {
        LOGGER.debug("{}.getAll", basicClass.getName());
        List<Book> books = entityManager
                .createNativeQuery(GET_ALL_BOOKS, Book.class)
                .getResultList();
        books.forEach(book -> book.getAuthors().size());
        return books;
    }

    @Override
    public boolean deleteBooks(List<Integer> ids) {
        LOGGER.debug("{}.deleteBooks({})", basicClass.getName(), ids);
        return false;
    }

    @Override
    protected boolean isInvalidEntity(Book book) {
        LOGGER.debug("{}.isInvalidEntity({})", basicClass.getName(), book);
        return super.isInvalidEntity(book) || StringUtils.isBlank(book.getName())
                || Objects.isNull(book.getIsbn())
                || CollectionUtils.isEmpty(book.getAuthors())
                || isInValidYearOfPublisher(book);
    }

    @Override
    protected boolean isInvalidEntityId(Book book) {
        LOGGER.debug("{}.isInvalidEntityId({})", basicClass.getName(), book);
        return Objects.isNull(book.getId());
    }

    private boolean isInValidYearOfPublisher(Book book) {
        if (Objects.isNull(book.getYearPublisher())) {
            return false;
        }
        return book.getYearPublisher() < 0
                || book.getYearPublisher() > LocalDate.now().getYear();
    }
}
