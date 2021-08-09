package com.softserve.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "authors_books")
public class AuthorBook implements Serializable {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    private Author author;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    private Book book;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        AuthorBook that = (AuthorBook) o;

        return new EqualsBuilder()
                .append(author, that.author)
                .append(book, that.book)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(author)
                .append(book)
                .toHashCode();
    }
}
