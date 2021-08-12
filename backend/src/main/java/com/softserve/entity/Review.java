package com.softserve.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "reviews")
public class Review implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(name = "commenter_name", length = 32, nullable = false)
    private String commenterName;

    @Column(name = "comment", length = 256, nullable = false)
    private String comment;

    @Column(name = "rating")
    private int rating;

    @Column(name = "create_date", nullable = false, updatable = false)
    private LocalDateTime createDate;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Book book;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Review review = (Review) o;

        return new EqualsBuilder()
                .append(rating, review.rating)
                .append(commenterName, review.commenterName)
                .append(comment, review.comment)
                .append(createDate, review.createDate)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(commenterName)
                .append(comment)
                .append(rating)
                .append(createDate)
                .toHashCode();
    }
}
