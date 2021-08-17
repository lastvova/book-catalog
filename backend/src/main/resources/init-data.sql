DROP DATABASE book_catalog;

CREATE DATABASE IF NOT EXISTS book_catalog;

USE book_catalog;

CREATE TABLE authors
(
    id           BIGINT UNSIGNED AUTO_INCREMENT,
    first_name   VARCHAR(32) NOT NULL,
    second_name  VARCHAR(32) NOT NULL,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT authors_pk PRIMARY KEY (id)
);

CREATE TABLE books
(
    id             BIGINT UNSIGNED AUTO_INCREMENT,
    name           VARCHAR(128) NOT NULL,
    year_publisher DATE,
    isbn           BIGINT       NOT NULL,
    publisher      VARCHAR(128) NOT NULL,
    created_date   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT books_pk PRIMARY KEY (id),
    CONSTRAINT books_isbn_uq UNIQUE (isbn)
);

CREATE TABLE reviews
(
    id             BIGINT UNSIGNED AUTO_INCREMENT,
    commenter_name VARCHAR(32)      NOT NULL,
    comment        VARCHAR(256)     NOT NULL,
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
    CONSTRAINT authors_books_author_id_fk FOREIGN KEY (author_id) REFERENCES authors (id),
    CONSTRAINT authors_books_book_id_fk FOREIGN KEY (book_id) REFERENCES books (id) ON DELETE CASCADE
);

INSERT INTO authors(first_name, second_name)
VALUES ('Leo', 'Tolstoy'),
       ('William', 'Shakespeare'),
       ('Herman', 'Melville'),
       ('Fyodor', 'Dostoyevsky'),
       ('John Ronald Reuel', 'Tolkien'),
       ('Dan', 'Brown');

INSERT INTO books(name, year_publisher, isbn, publisher)
VALUES ('War and Peace', '2009-01-01', 1427038651, 'Read How You Want'),
       ('Anna Karenina', '1981-02-02', 9780553210347, 'Bantam Books'),
       ('Hamlet', '2005-03-21', 142092253, 'Digireads'),
       ('King Lear', '2018-01-18', 9781980765707, 'Independently published'),
       ('Moby Dick', '1993-11-21', 9781566193566, 'Barnes & Noble'),
       ('Crime and Punishment', '2011-6-02', 1613821824, 'Simon & Brown'),
       ('The Lord of the Rings', '2021-07-05', 9780358653035, 'Mariner Books'),
       ('The Silmarillion', '2013-01-23', 9780007523221, 'Houghton Mifflin'),
       ('The Hobbit: Or, There and Back Again', '2001-9-26', 9780618150823, 'Young Readers Paperback Tolkien'),
       ('Inferno', '2011-6-02', 1234567890, 'Timon $ Pumba');

INSERT INTO authors_books(author_id, book_id)
values (1, 1),
       (1, 2),
       (2, 3),
       (2, 4),
       (3, 5),
       (4, 6),
       (5, 7),
       (5, 8),
       (6, 10),
       (5, 10),
       (4, 5);

INSERT INTO reviews(commenter_name, comment, rating, book_id)
VALUES ('Adam', 'Would absolutely recommend to any Tolkien fan or fan of fantasy/literature in general.', 5, 8),
       ('Demonsub',
        'It’s by far my favourite book of the year so far and I certainly look forward to rereading it in years to come.',
        5, 7),
       ('Adam', 'Would absolutely recommend to any Tolkien fan or fan of fantasy/literature in general.', 5, 8),
       ('Nick', 'Not bad', 3, 1),
       ('Anna', 'not my', 2, 1),
       ('Andy', 'I am very happy to have read this book', 5, 1),
       ('Samanta', 'Not so good', 3, 1),
       ('John', 'Good', 4, 2),
       ('Gerald', 'didnt like', 2, 2),
       ('Lola', 'Good', 5, 2),
       ('Steve', 'fooo', 1, 2),
       ('Andrew', 'nice', 4, 3),
       ('Maks', 'i like it', 5, 3),
       ('Paul', '----', 4, 3),
       ('Rose', 'I enjoyed this book', 5, 3),
       ('Vivien', 'not my', 1, 3);