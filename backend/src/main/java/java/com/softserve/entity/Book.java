package java.com.softserve.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//hot swap
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "book")
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(name = "name", length = 60, nullable = false)
    private String name;

    @Column(name = "year_publisher", nullable = false)
    private LocalDate yearPublisher;

    @Column(name = "isbn", unique = true, nullable = false)
    @NaturalId
    private BigInteger isbn;

    @Column(name = "publisher", length = 128, nullable = false)
//    more symbols like > 128
    private String publisher;

    @Column(name = "create_date", nullable = false, updatable = false)
    private LocalDateTime createDate;

    @OneToMany(
            mappedBy = "book",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<AuthorBook> authors = new ArrayList<>();

    @OneToMany(
            mappedBy = "book",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Review> reviews = new ArrayList<>();

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
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", yearPublisher=" + yearPublisher +
                ", isbn='" + isbn + '\'' +
                ", publisher='" + publisher + '\'' +
                ", createDate=" + createDate +
                ", authors=" + authors +
                ", reviews=" + reviews +
                '}';
    }
}

