package com.softserve.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.NaturalId;

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
@NoArgsConstructor
@Entity
@Table(name = "books")
public class Book implements Serializable {
    private static final long serialVersionUID = -4426773266096656069L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(name = "name", length = 128, nullable = false)
    private String name;

    @Column(name = "year_publisher")
    private Integer yearPublisher;

    @Column(name = "isbn", unique = true, nullable = false)
    @NaturalId(mutable = true)
    private BigInteger isbn;

    @Column(name = "publisher", length = 256)
    private String publisher;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss[.SSS][.SS][.S]")
    @Column(name = "created_date", insertable = false, updatable = false)
    private LocalDateTime createdDate;

    //    @PostLoad
    @Formula(value = "(select ifnull(round(avg(r.rating), 2),0) from reviews r where r.book_id = id)")
    private BigDecimal rating;

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(name = "authors_books",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<Author> authors = new HashSet<>();
}

