package com.softserve.entity;

import com.softserve.entity.interfaces.GeneralMethodsInterface;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
@Entity
@ToString
@Table(name = "reviews")
public class Review implements Serializable, GeneralMethodsInterface<BigInteger> {
    private static final long serialVersionUID = 1020476374006568471L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(name = "commenter_name", length = 256, nullable = false)
    private String commenterName;

    @Column(name = "comment", columnDefinition = "TEXT")
    private String comment;

    @Column(name = "rating", columnDefinition = "TINYINT", nullable = false)
    private Integer rating;

    @Column(name = "created_date", insertable = false, updatable = false, nullable = false)
    private LocalDateTime createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id",
            foreignKey = @ForeignKey(name = "reviews_book_id_fk"))
    private Book book;
}
