package com.softserve.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.NaturalId;

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

    //    TODO: another methods for avg rating
    //    TODO:cascade type
//    TODO @Fetch( FetchMode.SUBSELECT) -- крайні ситуації
//    TODO окремо пройтись по книжці, без книжки

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(name = "name", length = 64, nullable = false)
    private String name;

    @Column(name = "year_publisher")

    private LocalDate yearPublisher;

    @Column(name = "isbn", unique = true, nullable = false)
    @NaturalId
    private BigInteger isbn;

    @Column(name = "publisher", length = 128, nullable = false)
    private String publisher;

    @Column(name = "create_date", nullable = false, updatable = false)
    private LocalDateTime createDate;


    @Formula(value = "(select ifnull(round(avg(r.rating), 2),0) from reviews r where r.book_id = id)")
    private BigDecimal rating;

    @OneToMany(
            mappedBy = "book",
            cascade = CascadeType.REMOVE,
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        return new EqualsBuilder()
                .append(name, book.name)
                .append(yearPublisher, book.yearPublisher)
                .append(isbn, book.isbn)
                .append(publisher, book.publisher)
                .append(createDate, book.createDate)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(name)
                .append(yearPublisher)
                .append(isbn)
                .append(publisher)
                .append(createDate)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("yearPublisher", yearPublisher)
                .append("isbn", isbn)
                .append("publisher", publisher)
                .append("createDate", createDate)
                .append("rating", rating)
                .append("authors", authors)
                .append("reviews", reviews)
                .toString();
    }
}

