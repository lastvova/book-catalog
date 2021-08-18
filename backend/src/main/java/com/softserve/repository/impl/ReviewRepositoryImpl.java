package com.softserve.repository.impl;

import com.softserve.entity.Book;
import com.softserve.entity.Review;
import com.softserve.exception.WrongEntityException;
import com.softserve.repository.ReviewRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

@Repository
public class ReviewRepositoryImpl extends BaseRepositoryImpl<Review, BigInteger> implements ReviewRepository {

    private static final Logger log = LoggerFactory.getLogger(ReviewRepositoryImpl.class);

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Review save(Review review) {
        log.debug("In save of {}", basicClass.getName());
        if (!isValidEntity(review)) {
            throw new WrongEntityException("Wrong review in save method of repository");
        }
        if (!isBookExist(review.getBook().getId())) {
            throw new WrongEntityException("Wrong book in review");
        }
        Book book = entityManager.getReference(Book.class, review.getBook().getId());
        review.setBook(book);
        entityManager.persist(review);
        return review;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Review update(Review review) {
        log.debug("In update of {}", basicClass.getName());
        if (!isValidEntity(review)) {
            throw new WrongEntityException("Wrong review in update method of repository");
        }
        if (!isBookExist(review.getBook().getId())) {
            throw new WrongEntityException("Wrong book in review");
        }
        Book book = entityManager.getReference(Book.class, review.getBook().getId());
        review.setBook(book);
        entityManager.merge(review);
        return review;
    }

    @Override
    protected boolean isValidEntity(Review review) {
        log.debug("In isValidEntity of {}", basicClass.getName());
        return !StringUtils.isBlank(review.getComment()) && !StringUtils.isBlank(review.getCommenterName())
                && !Objects.isNull(review.getBook());
    }

    private boolean isBookExist(BigInteger id) {
        List<Book> list = entityManager.createQuery("select b from Book where id = :id", Book.class)
                .setParameter("id", id)
                .getResultList();
        return !CollectionUtils.isEmpty(list);
    }
}
