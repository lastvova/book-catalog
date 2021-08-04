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
@Table(name = "review")
public class Review implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(name = "commenter_name", length = 20, nullable = false)
    private String commenterName;

    @Column(name = "comment", length = 200, nullable = false)
    private String comment;

    @Column(name = "rating")
    private int rating;

    @Column(name = "create_date", nullable = false, updatable = false)
    private LocalDateTime createDate;

    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "book_id", foreignKey = @ForeignKey(name = "BOOK_ID_FK"))
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
                .append(book, review.book)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(commenterName)
                .append(comment)
                .append(rating)
                .append(createDate)
                .append(book)
                .toHashCode();
    }
}
