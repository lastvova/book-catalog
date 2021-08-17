package com.softserve.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.NaturalId;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "books")
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(name = "name", length = 64, nullable = false)
    private String name;

    @Column(name = "year_publisher")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate yearPublisher;

    @Column(name = "isbn", unique = true, nullable = false)
    @NaturalId(mutable = true)
    private BigInteger isbn;

    @Column(name = "publisher", length = 128, nullable = false)
    private String publisher;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss[.SSS][.SS][.S]")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "create_date", updatable = false)
    private LocalDateTime createDate;

    @Formula(value = "(select ifnull(round(avg(r.rating), 2),0) from reviews r where r.book_id = id)")
    private BigDecimal rating;

    @OneToMany(
            mappedBy = "book",
            cascade = CascadeType.MERGE,
            orphanRemoval = true
    )
    private Set<AuthorBook> authors = new HashSet<>();

    @OneToMany(
            mappedBy = "book",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Review> reviews = new HashSet<>();

    private void setAuthors(Set<AuthorBook> authors) {
        this.authors = authors;
    }

    private void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public void addReview(Review review) {
        reviews.add(review);
        review.setBook(this);
    }

    public void removeReview(Review review) {
        reviews.remove(review);
        review.setBook(null);
    }

    public void addAuthor(Author author) {
        AuthorBook authorBook = new AuthorBook(author, this);
        authors.add(authorBook);
        author.getBooks().add(authorBook);
    }

    public void removeAuthor(Author author) {
        AuthorBook authorBook = new AuthorBook(author, this);
        author.getBooks().remove(authorBook);
        authors.remove(authorBook);
        authorBook.setBook(null);
        authorBook.setAuthor(null);
    }
}

