package com.softserve.repository.impl;

import com.softserve.entity.Review;
import com.softserve.repository.ReviewRepository;
import com.softserve.utils.ListParams;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigInteger;

@Repository
public class ReviewRepositoryImpl extends BaseRepositoryImpl<Review, BigInteger> implements ReviewRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewRepositoryImpl.class);

    @Override
    public Predicate getPredicate(ListParams<?> listParams, Root<Review> entityRoot) {
        return null;
    }

    @Override
    protected boolean isInvalidEntity(Review review) {
        LOGGER.debug("isInvalidEntityId({})", review);
        return super.isInvalidEntity(review) || StringUtils.isBlank(review.getComment())
                || StringUtils.isBlank(review.getCommenterName())
                || review.getBook() == null || review.getRating() == null
                || review.getRating() <= 0 || review.getRating() > 5;
    }

    @Override
    protected boolean isInvalidEntityId(Review entity) {
        return entity.getId() == null;
    }
}
