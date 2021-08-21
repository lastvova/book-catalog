package com.softserve.service.impl;

import com.softserve.entity.Review;
import com.softserve.exception.WrongInputValueException;
import com.softserve.repository.ReviewRepository;
import com.softserve.service.ReviewService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

@Service
public class ReviewServiceImpl extends BaseServiceImpl<Review, BigInteger> implements ReviewService {

    private static final Logger log = LoggerFactory.getLogger(ReviewServiceImpl.class);
    private final ReviewRepository repository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public List<Review> getReviewsByBookId(BigInteger id) {
        log.debug("Enter into getReviewsByBookId method of ReviewServiceImpl with input value: [{}]", id);
        if (Objects.isNull(id)) {
            throw new WrongInputValueException("Wrong entity id :" + id);
        }
        return repository.getReviewsByBookId(id);
    }

    @Override
    public boolean isInvalidEntity(Review review) {
        log.debug("Enter into isInvalidEntity method of ReviewServiceImpl with input value: [{}]", review);
        return super.isInvalidEntity(review) || StringUtils.isBlank(review.getCommenterName())
                || StringUtils.isBlank(review.getComment());
    }
}
