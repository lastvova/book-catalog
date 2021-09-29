DROP DATABASE book_catalog;

CREATE DATABASE IF NOT EXISTS book_catalog;

USE book_catalog;

CREATE TABLE authors
(
    id           BIGINT UNSIGNED AUTO_INCREMENT,
    first_name   VARCHAR(128) NOT NULL,
    second_name  VARCHAR(128),
    rating       DECIMAL(3, 2) DEFAULT 0.00,
    created_date TIMESTAMP     DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT authors_pk PRIMARY KEY (id)
);

CREATE TABLE books
(
    id             BIGINT UNSIGNED AUTO_INCREMENT,
    name           VARCHAR(256) NOT NULL,
    year_publisher INT check ( year_publisher ),
    isbn           BIGINT       NOT NULL,
    publisher      VARCHAR(512),
    rating         DECIMAL(3, 2) DEFAULT 0.00,
    created_date   TIMESTAMP     DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT books_pk PRIMARY KEY (id),
    CONSTRAINT books_isbn_uq UNIQUE (isbn)
);

CREATE TABLE reviews
(
    id             BIGINT UNSIGNED AUTO_INCREMENT,
    commenter_name VARCHAR(256)     NOT NULL,
    comment        TEXT             NOT NULL,
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

DROP PROCEDURE IF EXISTS count_authors_rating_by_book;
CREATE PROCEDURE count_authors_rating_by_book(book_id_id INT)
BEGIN

    #   @authorIds is a string which contains array of author_id.
    SET @authorIds = (SELECT CONCAT(GROUP_CONCAT(author_id), ',') FROM authors_books WHERE book_id = book_id_id);
    WHILE (LOCATE(',', @authorIds) > 0)
        DO
#           get author_id and assign it into @value
            SET @value = SUBSTRING(@authorIds, 1, LOCATE(',', @authorIds) - 1);
#           truncate first author_id and assign new result into @authorIds
            SET @authorIds = SUBSTRING(@authorIds, LOCATE(',', @authorIds) + 1);
            CALL count_author_rating(@value);
        END WHILE;
END;

DROP PROCEDURE IF EXISTS count_author_rating;
CREATE PROCEDURE count_author_rating(author_id INT)
BEGIN
    UPDATE authors AS a
    SET rating = IFNULL((SELECT AVG(b.rating)
                         FROM books AS b
                         WHERE b.id IN (
                             SELECT ab.book_id
                             FROM authors_books AS ab
                             WHERE ab.author_id = author_id)
                           AND b.rating > 0), 0.00)
    WHERE a.id = author_id;
END;

DELIMITER //
DROP TRIGGER IF EXISTS count_rating_when_insert_review//
CREATE TRIGGER count_rating_when_insert_review
    AFTER INSERT
    ON reviews
    FOR EACH ROW
BEGIN
    DECLARE avg_rating_for_book DECIMAL(3, 2);
    SELECT avg(rating) INTO avg_rating_for_book FROM reviews WHERE book_id = NEW.book_id;

    UPDATE books AS b
    SET b.rating = avg_rating_for_book
    WHERE b.id = NEW.book_id;

    CALL count_authors_rating_by_book(NEW.book_id);
END//
DELIMITER ;

DELIMITER //
DROP TRIGGER IF EXISTS count_rating_when_delete_book//
CREATE TRIGGER count_rating_when_delete_book
    AFTER DELETE
    ON authors_books
    FOR EACH ROW
BEGIN
    CALL count_author_rating(OLD.author_id);
END//
DELIMITER ;

# Next trigger was created for case when we will can delete the separate review
#
# DELIMITER //
# DROP TRIGGER IF EXISTS count_rating_when_delete_review//
# CREATE TRIGGER count_rating_when_delete_review
#     AFTER UPDATE
#     ON reviews
#     FOR EACH ROW
# BEGIN
#     DECLARE avg_rating_for_book DECIMAL(3, 2);
#     SELECT avg(rating) INTO avg_rating_for_book FROM reviews WHERE book_id = OLD.book_id;
#
#     UPDATE books AS b
#     SET b.rating = avg_rating_for_book
#     WHERE b.id = OLD.book_id;
#
#     CALL count_authors_rating_by_book(OLD.book_id);
# END//
# DELIMITER ;
