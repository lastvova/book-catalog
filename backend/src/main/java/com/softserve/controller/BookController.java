package com.softserve.controller;

import com.softserve.dto.BookDTO;
import com.softserve.entity.Book;
import com.softserve.exception.EntityNotFoundException;
import com.softserve.exception.WrongEntityException;
import com.softserve.mapper.BookMapper;
import com.softserve.service.BookService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/books")
public class BookController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);
    private final BookService bookService;
    private final BookMapper bookMapper;

    @Autowired
    public BookController(BookService bookService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;

    }

    @GetMapping("")
    public ResponseEntity<List<BookDTO>> getAll() {
        LOGGER.debug("{}.getALl()", this.getClass().getName());
        List<BookDTO> bookDTOS = bookMapper.convertToDtoListWithAuthors(bookService.getAll());
        return ResponseEntity.status(HttpStatus.OK).body(bookDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> get(@PathVariable BigInteger id) {
        LOGGER.debug("{}.get({})", this.getClass().getName(), id);
        BookDTO bookDTO = bookMapper.convertToDto(bookService.getById(id));
        return ResponseEntity.status(HttpStatus.OK).body(bookDTO);
    }

    @PostMapping("")
    public ResponseEntity<BookDTO> create(@RequestBody BookDTO bookDTO) {
        LOGGER.debug("{}.create({})", this.getClass().getName(), bookDTO);
        if (!Objects.isNull(bookDTO.getId()) || isInvalidBook(bookDTO)) {
            throw new WrongEntityException("Wrong book in save method");
        }
        Book book = bookService.create(bookMapper.convertToEntity(bookDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(bookMapper.convertToDto(book));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> update(@PathVariable BigInteger id, @RequestBody BookDTO bookDTO) {
        LOGGER.debug("{}.update(id = {} dto = {})", this.getClass().getName(), id, bookDTO);
        if (!Objects.equals(id, bookDTO.getId())) {
            throw new EntityNotFoundException("Book id not equals provided id");
        }
        if (isInvalidBook(bookDTO)) {
            throw new WrongEntityException("Wrong book in update method");
        }
        bookService.update(bookMapper.convertToEntity(bookDTO));
        return ResponseEntity.status(HttpStatus.OK).body(bookDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable BigInteger id) {
        LOGGER.debug("{}.delete({})", this.getClass().getName(), id);
        bookService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Book was deleted");
    }

    @GetMapping("/rating")
    public ResponseEntity<List<BookDTO>> searchByRating(@RequestParam Integer rating) {
        LOGGER.debug("{}.searchByRating({})", this.getClass().getName(), rating);
        if (Objects.isNull(rating) || rating <= 0 || rating > 6) {
            throw new IllegalStateException("Wrong input rating for search");
        }
        List<BookDTO> bookDTOS = bookMapper.convertToDtoListWithAuthors(null);//TODO
        return ResponseEntity.status(HttpStatus.OK).body(bookDTOS);
    }

    private boolean isInvalidBook(BookDTO bookDTO) {
        return Objects.isNull(bookDTO) || StringUtils.isBlank(bookDTO.getName())
                || Objects.isNull(bookDTO.getIsbn())
                || CollectionUtils.isEmpty(bookDTO.getAuthors())
                || isInValidYearOfPublisher(bookDTO);
    }

    private boolean isInValidYearOfPublisher(BookDTO bookDTO) {
        if (Objects.isNull(bookDTO.getYearPublisher())) {
            return false;
        }
        return bookDTO.getYearPublisher() < 0
                || bookDTO.getYearPublisher() > LocalDate.now().getYear();
    }
}
