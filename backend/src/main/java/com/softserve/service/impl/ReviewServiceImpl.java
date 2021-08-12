package com.softserve.service.impl;

import com.softserve.entity.Review;
import com.softserve.repository.ReviewRepository;
import com.softserve.service.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Review findById(BigInteger id) {
        return repository.findById(id).get();
    }

    @Override
    public List<Review> getAll() {
        return repository.getAll();
    }

    @Override
    public Review save(Review entity) {
        return repository.save(entity);
    }

    @Override
    public Review update(Review entity) {
        return repository.update(entity);
    }

    @Override
    public Review delete(BigInteger id) {
        return repository.delete(id);
    }
}
