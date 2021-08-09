package com.softserve.repository.impl;

import com.softserve.entity.Review;
import com.softserve.repository.ReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Slf4j
@Repository
public class ReviewRepositoryImpl extends BasicRepositoryImpl<Review, BigInteger> implements ReviewRepository {
}
