package com.softserve.repository.impl;

import com.softserve.entity.Author;
import com.softserve.entity.Book;
import com.softserve.exception.DeleteAuthorWithBooksException;
import com.softserve.repository.AuthorRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

@Repository
public class AuthorRepositoryImpl extends BaseRepositoryImpl<Author, BigInteger> implements AuthorRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorRepositoryImpl.class);

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Book> getBooksByAuthorId(BigInteger id) {
        LOGGER.debug("{}.getBooksByAuthorId({})", basicClass.getName(), id);
        if (Objects.isNull(id)) {
            throw new IllegalStateException("Wrong id");
        }
        return entityManager.createQuery("select b from Book b " +
                        "join b.authors a " +
                        "where a.id = :authorId", Book.class)
                .setParameter("authorId", id)
                .getResultList();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public boolean hasBooks(BigInteger id) {
        LOGGER.debug("{}.hasBooks({})", basicClass.getName(), id);
        if (Objects.isNull(id)) {
            throw new IllegalStateException("Wrong author id");
        }
        BigInteger count = (BigInteger) entityManager  // todo: "exists" will return boolean value!!! java.math.BigInteger cannot be cast to java.lang.Boolean
                .createNativeQuery("select exists (select 1 from authors_books a " +
                        "where a.author_id = :id)")
                .setParameter("id", id)
                .getSingleResult();
        return !Objects.isNull(count);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public boolean delete(BigInteger id) {
        LOGGER.debug("{}.delete({})", basicClass.getName(), id);
        if (Objects.isNull(id)) {
            throw new IllegalStateException("Wrong author id");
        }
        Author author = getById(id); // todo: why are you need this read?
        if (Objects.isNull(author)) {
            return false;
        }
        if (hasBooks(id)) {
            throw new DeleteAuthorWithBooksException("Cant delete author, because it has books");
        }
        entityManager.remove(author);
        return true;
    }

    @Override
    public boolean deleteAuthors(List<Integer> ids) { //TODO
        LOGGER.debug("{}.deleteAuthors({})", basicClass.getName(), ids);
        return false;
    }

    @Override
    public boolean isInvalidEntity(Author author) {
        LOGGER.debug("{}.isInvalidEntity({})", basicClass.getName(), author);
        return super.isInvalidEntity(author) || StringUtils.isBlank(author.getFirstName());
    }

    @Override
    protected boolean isInvalidEntityId(Author author) {
        LOGGER.debug("{}.isInvalidEntityId({})", basicClass.getName(), author);
        return Objects.isNull(author.getId());
    }
}
