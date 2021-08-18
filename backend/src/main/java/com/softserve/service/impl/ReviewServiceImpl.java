package com.softserve.service.impl;

import com.softserve.entity.Review;
import com.softserve.exception.IncorrectFieldException;
import com.softserve.repository.ReviewRepository;
import com.softserve.service.ReviewService;
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
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository repository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Review getById(BigInteger id) {
        return repository.getById(id);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Review> getAll() {
        return repository.getAll();
    }

    @Override
    @Transactional
    public Review save(Review review) {
        isInvalidReview(review);
        return repository.save(review);
    }

    @Override
    @Transactional
    public Review update(Review review) {
        isInvalidReview(review);
        return repository.update(review);
    }

    @Override
    @Transactional
    public boolean delete(BigInteger id) {
        Review review = getById(id);
        return repository.delete(review.getId());
    }

    private void isInvalidReview(Review review) {
        if (StringUtils.isBlank(review.getCommenterName()) || StringUtils.isBlank(review.getComment())) {
            throw new IncorrectFieldException("Review has nulls or whitespaces in commenterName or comment");
        }
    }
}
