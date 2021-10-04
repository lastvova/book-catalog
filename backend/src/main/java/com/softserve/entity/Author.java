package com.softserve.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "authors")
public class Author implements Serializable {
    private static final long serialVersionUID = -7910979735208650001L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(name = "first_name", length = 128, nullable = false)
    private String firstName;

    @Column(name = "second_name", length = 128)
    private String secondName;

    @Column(name = "created_date", insertable = false, updatable = false, nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "rating", insertable = false, updatable = false, nullable = false)
    private BigDecimal rating;

    @ManyToMany(mappedBy = "authors")
    private Set<Book> books = new HashSet<>();
}
