package com.softserve.repository;

import com.softserve.entity.Review;

import java.math.BigInteger;
import java.util.List;

public interface ReviewRepository extends BaseRepository<Review, BigInteger> {

    List<Review> getReviewsByBookId(BigInteger bookId);
}
