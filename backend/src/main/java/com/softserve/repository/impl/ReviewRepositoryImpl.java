package com.softserve.repository.impl;

import com.softserve.entity.Book;
import com.softserve.entity.Review;
import com.softserve.exception.EntityNotFoundException;
import com.softserve.exception.WrongEntityException;
import com.softserve.exception.WrongInputValueException;
import com.softserve.repository.ReviewRepository;
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
        log.debug("In save method with input value: [{}] of {}", review, basicClass.getName());
        if (isInvalidEntity(review) || isBookNotExist(review.getBook().getId())) {
            throw new WrongEntityException("Wrong review in save method ");
        }
        Book book = entityManager.getReference(Book.class, review.getBook().getId());
        review.setBook(book);
        entityManager.persist(review);
        return review;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Review update(Review review) {
        log.debug("In update method with input value: [{}] of {}", review, basicClass.getName());
        if (isInvalidEntity(review) || isBookNotExist(review.getBook().getId())) {
            throw new WrongEntityException("Wrong review in update method ");
        }
        Book book = entityManager.getReference(Book.class, review.getBook().getId());
        review.setBook(book);
        entityManager.merge(review);
        return review;
    }

    @Override
    public List<Review> getReviewsByBookId(BigInteger bookId) {
        log.debug("In getReviewsByBookId method with input value: [{}] of {}", bookId, basicClass.getName());
        if (Objects.isNull(bookId)) {
            throw new WrongInputValueException("Wrong id = " + bookId);
        }
        return entityManager.createQuery("select r from Review r " +
                        "where r.book.id = :bookId", Review.class)
                .setParameter("bookId", bookId)
                .getResultList();
    }

    @Override
    protected boolean isInvalidEntity(Review review) {
        log.debug("In isInvalidEntity method with input value: [{}] of {}", review, basicClass.getName());
        return super.isInvalidEntity(review) || StringUtils.isBlank(review.getComment())
                || StringUtils.isBlank(review.getCommenterName())
                || Objects.isNull(review.getBook()) || Objects.isNull(review.getRating())
                || review.getRating() <= 0 || review.getRating() > 5;
    }

    private boolean isBookNotExist(BigInteger id) {
        log.debug("In isBookNoExist method with input value: [{}] of {}", id, basicClass.getName());
        BigInteger count = (BigInteger) entityManager
                .createNativeQuery("select exists (select 1 from books b " +
                        "where b.id = :id)")
                .setParameter("id", id)
                .getSingleResult();
        return !Objects.isNull(count);
    }
}
