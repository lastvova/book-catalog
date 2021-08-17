package com.softserve.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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
