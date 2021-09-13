package com.softserve.service.impl;

import com.softserve.entity.Author;
import com.softserve.entity.Book;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorServiceImpl.class);
    private final AuthorRepository repository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Book> getBooksByAuthorId(BigInteger id) {
        LOGGER.debug("{}.getBooksByAuthorId({})", this.getClass().getName(), id);
        if (Objects.isNull(id)) {
            throw new IllegalStateException("Wrong author id");
        }
        return null;//TODO
    }

    @Override
    public boolean isInvalidEntity(Author author) {
        LOGGER.debug("{}.isInvalidEntity({})", this.getClass().getName(), author);
        return super.isInvalidEntity(author) || StringUtils.isBlank(author.getFirstName());
    }
}
