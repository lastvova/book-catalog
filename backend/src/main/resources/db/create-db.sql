DROP DATABASE book_catalog;

CREATE DATABASE IF NOT EXISTS book_catalog;

USE book_catalog;

CREATE TABLE authors
(
    id           BIGINT UNSIGNED AUTO_INCREMENT,
    first_name   VARCHAR(128) NOT NULL,
    second_name  VARCHAR(128),
    rating         DECIMAL(3, 2) DEFAULT 0.00,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT authors_pk PRIMARY KEY (id)
);

CREATE TABLE books
(
    id             BIGINT UNSIGNED AUTO_INCREMENT,
    name           VARCHAR(256) NOT NULL,
    year_publisher INT,
    isbn           BIGINT       NOT NULL,
    publisher      VARCHAR(512),
    rating         DECIMAL(3, 2) DEFAULT 0.00,
    created_date   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT books_pk PRIMARY KEY (id),
    CONSTRAINT books_isbn_uq UNIQUE (isbn)
);

CREATE TABLE reviews
(
    id             BIGINT UNSIGNED AUTO_INCREMENT,
    commenter_name VARCHAR(256)     NOT NULL,
    comment        TEXT(4096) NOT NULL,
    rating         TINYINT UNSIGNED NOT NULL,
    book_id        BIGINT UNSIGNED  NOT NULL,
    created_date   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT reviews_pk PRIMARY KEY (id),
    CONSTRAINT reviews_rating_chk CHECK ( rating > 0 AND rating <= 5),
    CONSTRAINT reviews_book_id_fk FOREIGN KEY (book_id) REFERENCES books (id) ON DELETE CASCADE
);

DELIMITER //
DROP TRIGGER IF EXISTS count_rating//
CREATE TRIGGER count_rating
    AFTER INSERT
    ON reviews
    FOR EACH ROW
BEGIN
    DECLARE avg_rating_for_book DECIMAL(3, 2);
    SELECT avg(rating) INTO avg_rating_for_book FROM reviews WHERE book_id = NEW.book_id;

    UPDATE books AS b
    SET b.rating = avg_rating_for_book
    WHERE b.id = NEW.book_id;

    UPDATE authors AS a
        INNER JOIN authors_books AS ab ON a.id = ab.author_id
    SET a.rating = (SELECT AVG(b.rating)
                    FROM books AS b
                    WHERE b.id IN (select book_id
                                   from authors_books ab1
                                   where ab1.author_id in (SELECT author_id
                                                           FROM authors_books AS ab
                                                           WHERE ab.book_id = NEW.book_id)))
    WHERE a.id IN (SELECT author_id FROM authors_books AS ab WHERE ab.book_id = NEW.book_id);
END//
DELIMITER ;

CREATE TABLE authors_books
(
    author_id BIGINT UNSIGNED NOT NULL,
    book_id   BIGINT UNSIGNED NOT NULL,
    CONSTRAINT authors_books_pk PRIMARY KEY (author_id, book_id),
    CONSTRAINT authors_books_author_id_fk FOREIGN KEY (author_id) REFERENCES authors (id) ON DELETE CASCADE,
    CONSTRAINT authors_books_book_id_fk FOREIGN KEY (book_id) REFERENCES books (id) ON DELETE CASCADE
);