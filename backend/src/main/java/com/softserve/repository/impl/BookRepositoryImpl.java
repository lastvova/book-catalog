package com.softserve.repository.impl;

import com.softserve.entity.Book;
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

    @Override
    public Book getById(BigInteger id) {
        LOGGER.debug("{}.getById({})", basicClass.getName(), id);
        if (Objects.isNull(id)) {
            throw new IllegalStateException("Wrong book id");
        }
        // todo: how authors will appear in your Book entity?
        return entityManager.createQuery("select b from Book b " +
                        "join fetch b.authors " +
                        "where b.id = :id", Book.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public List<Book> getAll() {
        LOGGER.debug("{}.getAll", basicClass.getName());
        return entityManager.createQuery("select b from Book b " +
                        "join fetch b.authors", Book.class)
                .getResultList();
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
