package com.softserve.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Getter
@Setter
//TODO redundant annotation
@NoArgsConstructor
@Entity
@Table(name = "reviews")
public class Review implements Serializable {
    private static final long serialVersionUID = 1020476374006568471L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(name = "commenter_name", length = 128, nullable = false)
    private String commenterName;

    @Column(name = "comment", length = 1024, nullable = false)
    private String comment;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    //TODO nullable false?
    @Column(name = "created_date", insertable = false, updatable = false)
    private LocalDateTime createdDate;

    //TODO Lazy is default
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id",
            foreignKey = @ForeignKey(name = "reviews_book_id_fk"))
    private Book book;
}
