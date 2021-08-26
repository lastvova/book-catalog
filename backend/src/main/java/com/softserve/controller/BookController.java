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

    private static final Logger log = LoggerFactory.getLogger(BookController.class);
    private final BookService bookService;
    private final BookMapper bookMapper;

    @Autowired
    public BookController(BookService bookService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;

    }

    @GetMapping("")
    public ResponseEntity<List<BookDTO>> getAll() {
        log.debug("Enter into getAll method of BookController");
        List<BookDTO> bookDTOS = bookMapper.convertToDtoListWithAuthors(bookService.getAll());
        return ResponseEntity.status(HttpStatus.OK).body(bookDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> get(@PathVariable BigInteger id) {
        log.debug("Enter into get method of BookController with input value: {}", id);
        BookDTO bookDTO = bookMapper.convertToDto(bookService.getById(id));
        return ResponseEntity.status(HttpStatus.OK).body(bookDTO);
    }

    @PostMapping("")
    public ResponseEntity<BookDTO> save(@RequestBody BookDTO bookDTO) {
        log.debug("Enter into save method of BookController with input value: [{}]", bookDTO);
        if (!Objects.isNull(bookDTO.getId()) || isInvalidBook(bookDTO)) {
            throw new WrongEntityException("Wrong book in save method");
        }
        Book book = bookService.create(bookMapper.convertToEntity(bookDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(bookMapper.convertToDto(book));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> update(@PathVariable BigInteger id, @RequestBody BookDTO bookDTO) {
        log.debug("Enter into update method of BookController with input value: [{}]", bookDTO);
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
        log.debug("Enter into delete method of BookController with input value: [{}]", id);
        bookService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Book was deleted");
    }

    @GetMapping("/{id}/detail")
    public ResponseEntity<BookDTO> getBookInfo(@PathVariable BigInteger id) {
        log.debug("Enter into getFullInfo method of BookController with input value: {}", id);
        BookDTO bookDTO = bookMapper.convertToDto(bookService.getById(id));
        return ResponseEntity.status(HttpStatus.OK).body(bookDTO);
    }

    @GetMapping("/rating")
    public ResponseEntity<List<BookDTO>> searchByRating(@RequestParam Integer rating) {
        log.debug("Enter into searchByRating method of BookController");
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
