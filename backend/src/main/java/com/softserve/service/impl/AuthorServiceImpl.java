package com.softserve.service.impl;

import com.softserve.entity.Author;
import com.softserve.entity.Book;
import com.softserve.exception.WrongInputValueException;
import com.softserve.repository.AuthorRepository;
import com.softserve.service.AuthorService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

@Service
public class AuthorServiceImpl extends BaseServiceImpl<Author, BigInteger> implements AuthorService {

    private static final Logger log = LoggerFactory.getLogger(AuthorServiceImpl.class);
    private final AuthorRepository repository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Book> getBooksByAuthorId(BigInteger id) {
        log.debug("Enter into getBooksByAuthor method with input value: [{}]", id);
        if (Objects.isNull(id)) {
            throw new WrongInputValueException("Wrong author id :" + id);
        }
        return repository.getBooksByAuthorId(id);
    }

    @Override
    public boolean isInvalidEntity(Author entity) {
        log.debug("Enter into isInvalidEntity method with input value: [{}]", entity);
        return Objects.isNull(entity) || StringUtils.isBlank(entity.getFirstName());
    }
}
