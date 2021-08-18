package com.softserve.service.impl;

import com.softserve.entity.Author;
import com.softserve.entity.Book;
import com.softserve.exception.DeleteAuthorWithBooksException;
import com.softserve.exception.IncorrectFieldException;
import com.softserve.repository.AuthorRepository;
import com.softserve.service.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

@Slf4j
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository repository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Author getById(BigInteger id) {
        return repository.getById(id);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Author> getAll() {
        return repository.getAll();
    }

    @Override
    @Transactional
    public Author save(Author author) {
        isInvalidAuthor(author);
        return repository.save(author);
    }

    @Override
    @Transactional
    public Author update(Author entity) {
        isInvalidAuthor(entity);
        return repository.update(entity);
    }

    @Override
    @Transactional
    public boolean delete(BigInteger id) {
        Author author = getById(id);
        if (repository.hasBooks(author.getId())) {
            throw new DeleteAuthorWithBooksException("cant delete author with id = " + author.getId());
        }
        return repository.delete(author.getId());
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Book> getBooksByAuthorId(BigInteger id) {
        return repository.getBooksByAuthorId(id);
    }

    private void isInvalidAuthor(Author author) {
        if (StringUtils.isBlank(author.getFirstName()) || StringUtils.isBlank(author.getSecondName())) {
            throw new IncorrectFieldException("Author has nulls or whitespaces in name or secondName");
        }
    }
}
