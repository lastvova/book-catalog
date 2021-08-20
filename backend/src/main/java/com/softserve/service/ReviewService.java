package com.softserve.service;

import com.softserve.entity.Review;

import java.math.BigInteger;
import java.util.List;

public interface ReviewService extends BaseService<Review, BigInteger> {
    List<Review> getReviewsByBookId(BigInteger bookId);
}
