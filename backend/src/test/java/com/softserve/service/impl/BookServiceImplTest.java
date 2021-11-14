package com.softserve.service.impl;

import com.softserve.entity.Author;
import com.softserve.entity.Book;
import com.softserve.exception.EntityNotFoundException;
import com.softserve.exception.WrongEntityException;
import com.softserve.repository.BookRepository;
import com.softserve.utils.ListParams;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.math.BigInteger;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book book;

    @BeforeEach
    public void setUp() {
        book = new Book();
        book.setId(BigInteger.ONE);
        book.setName("Hobbit");
        book.setPublisher("Some publisher");
        book.setIsbn(BigInteger.valueOf(999_999_999_999_9L));
        book.setYearPublisher(2020);
        Set<Author> authors = new HashSet<>();
        authors.add(new Author() {
            {
                setId(BigInteger.TEN);
            }
        });
        book.setAuthors(authors);
    }


    @Test
    void getBookById() {
        Mockito.when(bookRepository.getById(BigInteger.ONE)).thenReturn(book);

        Book resultBook = bookService.getById(BigInteger.ONE);

        Assertions.assertNotNull(resultBook);
        Assertions.assertEquals(book, resultBook);
        Mockito.verify(bookRepository, Mockito.times(1)).getById(BigInteger.ONE);
    }

    @Test
    void throwEntityNotFoundExceptionIfBookNotFounded() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> bookService.getById(BigInteger.TEN));
        Mockito.verify(bookRepository, Mockito.times(1)).getById(BigInteger.TEN);
    }

    @Test
    void throwIllegalArgumentExceptionWhenIdIsNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> bookService.getById(null));
    }

    @Test
    void createBook() {
        book.setId(null);
        Mockito.when(bookRepository.create(book)).thenReturn(book);

        Book resultBook = bookService.create(book);

        Assertions.assertNotNull(resultBook);
        Assertions.assertEquals(book, resultBook);
        Mockito.verify(bookRepository, Mockito.times(1)).create(book);
    }

    @Test
    void throwWrongEntityExceptionWhenCreateBookWithId() {
        Assertions.assertThrows(WrongEntityException.class, () -> bookService.create(book));
    }

    @Test
    void throwWrongEntityExceptionWhenCreateBookIsbnIsNull() {
        book.setIsbn(null);

        Assertions.assertThrows(WrongEntityException.class, () -> bookService.create(book));
    }

    @Test
    void throwWrongEntityExceptionWhenCreateBookIsbnLessThanSymbols() {
        book.setIsbn(BigInteger.valueOf(9));

        Assertions.assertThrows(WrongEntityException.class, () -> bookService.create(book));
    }

    @Test
    void throwWrongEntityExceptionWhenCreateBookIsbnMoreThanSymbols() {
        book.setIsbn(BigInteger.valueOf(10_111_111_111_111L));

        Assertions.assertThrows(WrongEntityException.class, () -> bookService.create(book));
    }

    @Test
    void throwWrongEntityExceptionWhenCreateBookNameIsNull() {
        book.setName(null);

        Assertions.assertThrows(WrongEntityException.class, () -> bookService.create(book));
    }

    @Test
    void throwWrongEntityExceptionWhenCreateBookAuthorsIsEmpty() {
        book.setAuthors(Collections.emptySet());

        Assertions.assertThrows(WrongEntityException.class, () -> bookService.create(book));
    }

    @Test
    void throwWrongEntityExceptionWhenCreateBookYearIsWrong() {
        book.setYearPublisher(3333);

        Assertions.assertThrows(WrongEntityException.class, () -> bookService.create(book));
    }

    @Test
    void throwWrongEntityExceptionWhenCreateBookYearIsNull() {
        book.setYearPublisher(null);

        Assertions.assertThrows(WrongEntityException.class, () -> bookService.create(book));
    }

    @Test
    void updateBook() {
        Mockito.when(bookRepository.update(book)).thenReturn(book);

        Book resultBook = bookService.update(book);

        Assertions.assertNotNull(resultBook);
        Assertions.assertEquals(book, resultBook);
        Mockito.verify(bookRepository, Mockito.times(1)).update(book);
    }

    @Test
    void throwWrongEntityExceptionWhenUpdateBookIdIsNull() {
        book.setId(null);

        Assertions.assertThrows(WrongEntityException.class, () -> bookService.update(book));
    }

    @Test
    void deleteBook() {
        Mockito.when(bookRepository.delete(book.getId())).thenReturn(true);

        Assertions.assertTrue(bookService.delete(book.getId()));
        Mockito.verify(bookRepository, Mockito.times(1)).delete(book.getId());
    }

    @Test
    void throwIllegalArgumentExceptionWhenDeleteBookIdIsNull() {
        book.setId(null);

        Assertions.assertThrows(IllegalArgumentException.class, () -> bookService.delete(book.getId()));
    }

    @Test
    void bulkDeleteBook() {
        List<BigInteger> ids = Collections.singletonList(book.getId());

        Mockito.when(bookRepository.deleteBooks(ids)).thenReturn(true);

        Assertions.assertTrue(bookService.bulkDelete(ids));
        Mockito.verify(bookRepository, Mockito.times(1)).deleteBooks(ids);
    }

    @Test
    void throwWrongEntityExceptionWhenDeleteBookIdIsNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> bookService.bulkDelete(null));
    }

    @Test
    void getAll() {
        Page<Book> page = new PageImpl<>(Collections.singletonList(book));

        Mockito.when(bookRepository.getAll(Mockito.any(ListParams.class))).thenReturn(page);

        Page<Book> resultPage = bookService.getAll(new ListParams<>());

        Assertions.assertNotNull(resultPage);
        Assertions.assertEquals(page, resultPage);
        Assertions.assertEquals(page.getContent(), resultPage.getContent());
        Mockito.verify(bookRepository, Mockito.times(1)).getAll(Mockito.any(ListParams.class));
    }

    @Test
    void throwIllegalArgumentExceptionWhenGetAllParametersIsNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> bookService.getAll(null));
    }

}