package com.softserve.entity;

import com.softserve.entity.interfaces.GeneralMethodsInterface;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@ToString
@Table(name = "books")
public class Book implements Serializable, GeneralMethodsInterface<BigInteger> {
    private static final long serialVersionUID = -4426773266096656069L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(name = "name", length = 256, nullable = false)
    private String name;

    @Column(name = "year_publisher")
    private Integer yearPublisher;

    @Column(name = "isbn", unique = true, nullable = false)
    private BigInteger isbn;

    @Column(name = "publisher", length = 512)
    private String publisher;

    @Column(name = "created_date", insertable = false, updatable = false, nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "rating", insertable = false, updatable = false, nullable = false)
    private BigDecimal rating;

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(name = "authors_books",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<Author> authors = new HashSet<>();
}

