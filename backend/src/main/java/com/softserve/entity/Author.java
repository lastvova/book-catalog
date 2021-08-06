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
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "author")
public class Author implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(name = "first_name", length = 32, nullable = false)
    private String firstName;

    @Column(name = "second_name", length = 32, nullable = false)
    private String secondName;

    @Column(name = "create_date", nullable = false, updatable = false)
    private LocalDateTime createDate;

    @OneToMany(
            mappedBy = "author",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<AuthorBook> books = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Author author = (Author) o;

        return new EqualsBuilder()
                .append(firstName, author.firstName)
                .append(secondName, author.secondName)
                .append(createDate, author.createDate)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(19, 39)
                .append(id)
                .append(firstName)
                .append(secondName)
                .append(createDate)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", createDate=" + createDate +
                ", books=" + books +
                '}';
    }
}