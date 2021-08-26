package com.softserve.repository.impl;

import com.softserve.entity.Review;
import com.softserve.repository.ReviewRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Objects;

@Repository
public class ReviewRepositoryImpl extends BaseRepositoryImpl<Review, BigInteger> implements ReviewRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewRepositoryImpl.class);

    @Override
    protected boolean isInvalidEntity(Review review) {
        LOGGER.debug("In isInvalidEntity method with input value: [{}] of {}", review, basicClass.getName());
        return super.isInvalidEntity(review) || StringUtils.isBlank(review.getComment())
                || StringUtils.isBlank(review.getCommenterName())
                || Objects.isNull(review.getBook()) || Objects.isNull(review.getRating())
                || review.getRating() <= 0 || review.getRating() > 5;
    }

    @Override
    protected boolean isInvalidEntityId(Review entity) {
        return Objects.isNull(entity.getId());
    }
}
