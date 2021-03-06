package com.softserve.service.impl;

import com.softserve.entity.Author;
import com.softserve.repository.AuthorRepository;
import com.softserve.service.AuthorService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

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
    public boolean isInvalidEntity(Author author) {
        LOGGER.debug("isInvalidEntity({})", author);
        return super.isInvalidEntity(author) || StringUtils.isBlank(author.getFirstName());
    }

    @Override
    @Transactional
    public boolean bulkDelete(List<BigInteger> ids) {
        LOGGER.debug("bulkDelete({})", ids);
        return repository.deleteAuthors(ids);
    }
}
