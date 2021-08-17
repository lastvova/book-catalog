package com.softserve.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
