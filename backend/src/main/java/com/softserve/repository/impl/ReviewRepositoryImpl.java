package com.softserve.repository.impl;

import com.softserve.entity.Book;
import com.softserve.entity.Review;
import com.softserve.repository.ReviewRepository;
import com.softserve.utils.ListParams;
import com.softserve.utils.ReviewFilterParameters;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReviewRepositoryImpl extends BaseRepositoryImpl<Review, BigInteger> implements ReviewRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewRepositoryImpl.class);

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<Review> getAll(ListParams<?> params) {
        LOGGER.debug("getAll");
        Page<Review> reviews = super.getAll(params);
        reviews.getContent().forEach(review -> review.getBook().getName());
        return reviews;
    }

    @Override
    public Predicate getPredicate(ListParams<?> params, Root<Review> reviews) {
        List<Predicate> predicates = new ArrayList<>();
        if (params.getPattern() != null) {
            ReviewFilterParameters filterParameters = (ReviewFilterParameters) params.getPattern();

            if (filterParameters.getCommenterName() != null) {
                predicates.add(
                        criteriaBuilder.like(reviews.get("commenterName"),
                                "%" + filterParameters.getCommenterName() + "%")
                );
            }
            if (filterParameters.getFromRating() != null) {
                predicates.add(
                        criteriaBuilder.ge(reviews.get("rating"), filterParameters.getFromRating()));
            }
            if (filterParameters.getToRating() != null) {
                predicates.add(
                        criteriaBuilder.lt(reviews.get("rating"), filterParameters.getToRating()));
            }
            if (filterParameters.getBookName() != null) {
                Join<Review, Book> book = reviews.join("book");
                predicates.add(
                        criteriaBuilder.like(book.get("name"),
                                "%" + filterParameters.getBookName() + "%"));
            }
            if (filterParameters.getBookId() != null) {
                Join<Review, Book> book = reviews.join("book");
                predicates.add(
                        criteriaBuilder.equal(book.get("id"), filterParameters.getBookId()));
            }
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    //This overriding needs for correct count books with joining authors
    @Override
    protected long getEntityCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class); // todo: Please not use Long class here!
        Root<Review> reviews = countQuery.from(Review.class);
        reviews.join("book");
        countQuery.select(criteriaBuilder.countDistinct(reviews)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }

    @Override
    protected boolean isInvalidEntity(Review review) {
        LOGGER.debug("isInvalidEntityId({})", review);
        return super.isInvalidEntity(review) || StringUtils.isBlank(review.getCommenterName())
                || review.getBook() == null || review.getRating() == null
                || review.getRating() <= 0 || review.getRating() > 5;
    }

    @Override
    protected boolean isInvalidEntityId(Review entity) {
        return entity.getId() == null;
    }
}
