DROP DATABASE book_catalog;

CREATE DATABASE IF NOT EXISTS book_catalog;

USE book_catalog;

CREATE TABLE authors
(
    id           BIGINT UNSIGNED AUTO_INCREMENT,
    first_name   VARCHAR(128) NOT NULL,
    second_name  VARCHAR(128),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT authors_pk PRIMARY KEY (id)
);

CREATE TABLE books
(
    id             BIGINT UNSIGNED AUTO_INCREMENT,
    name           VARCHAR(128) NOT NULL,
    year_publisher INT,
    isbn           BIGINT       NOT NULL,
    publisher      VARCHAR(256),
    created_date   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT books_pk PRIMARY KEY (id),
    CONSTRAINT books_isbn_uq UNIQUE (isbn)
);

CREATE TABLE reviews
(
    id             BIGINT UNSIGNED AUTO_INCREMENT,
    commenter_name VARCHAR(128)     NOT NULL,
    comment        VARCHAR(1024)    NOT NULL,
    rating         TINYINT UNSIGNED NOT NULL,
    book_id        BIGINT UNSIGNED  NOT NULL,
    created_date   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT reviews_pk PRIMARY KEY (id),
    CONSTRAINT reviews_rating_chk CHECK ( rating > 0 AND rating <= 5),
    CONSTRAINT reviews_book_id_fk FOREIGN KEY (book_id) REFERENCES books (id) ON DELETE CASCADE
);

CREATE TABLE authors_books
(
    author_id BIGINT UNSIGNED NOT NULL,
    book_id   BIGINT UNSIGNED NOT NULL,
    CONSTRAINT authors_books_pk PRIMARY KEY (author_id, book_id),
    CONSTRAINT authors_books_author_id_fk FOREIGN KEY (author_id) REFERENCES authors (id) ON DELETE CASCADE,
    CONSTRAINT authors_books_book_id_fk FOREIGN KEY (book_id) REFERENCES books (id) ON DELETE CASCADE
);