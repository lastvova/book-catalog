package com.softserve.service.impl;

import com.softserve.entity.Review;
import com.softserve.repository.ReviewRepository;
import com.softserve.service.ReviewService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public class ReviewServiceImpl extends BaseServiceImpl<Review, BigInteger> implements ReviewService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewServiceImpl.class);
    private final ReviewRepository repository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public boolean isInvalidEntity(Review review) {
        LOGGER.debug("{}.isInvalidEntity({})", this.getClass().getName(), review);
        return super.isInvalidEntity(review) || StringUtils.isBlank(review.getCommenterName())
                || StringUtils.isBlank(review.getComment());
    }
}
