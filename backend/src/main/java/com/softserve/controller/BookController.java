package com.softserve.controller;

import com.softserve.dto.BookDTO;
import com.softserve.dto.BookInfoDTO;
import com.softserve.dto.BookWithAuthorsDTO;
import com.softserve.entity.Book;
import com.softserve.exception.EntityNotFoundException;
import com.softserve.exception.WrongEntityException;
import com.softserve.exception.WrongInputValueException;
import com.softserve.mapper.BookMapper;
import com.softserve.mapper.ReviewMapper;
import com.softserve.service.BookService;
import com.softserve.service.ReviewService;
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
    private final ReviewService reviewService;
    private final BookMapper bookMapper;
    private final ReviewMapper reviewMapper;

    @Autowired
    public BookController(BookService bookService, BookMapper bookMapper, ReviewService reviewService, ReviewMapper reviewMapper) {
        this.bookService = bookService;
        this.reviewService = reviewService;
        this.bookMapper = bookMapper;
        this.reviewMapper = reviewMapper;

    }

    @GetMapping("")
    public ResponseEntity<List<BookDTO>> getAll() {
        log.debug("Enter into getAll method of BookController");
        List<BookDTO> bookDTOS = bookMapper.convertToDtoList(bookService.getAll());
        return ResponseEntity.status(HttpStatus.OK).body(bookDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> get(@PathVariable BigInteger id) {
        log.debug("Enter into get method of BookController with input value: {}", id);
        BookDTO bookDTO = bookMapper.convertToDto(bookService.getById(id));
        return ResponseEntity.status(HttpStatus.OK).body(bookDTO);
    }

    @PostMapping("")
    public ResponseEntity<BookDTO> save(@RequestBody BookWithAuthorsDTO bookWithAuthorsDTO) {
        log.debug("Enter into save method of BookController with input value: [{}]", bookWithAuthorsDTO);
        if (!Objects.isNull(bookWithAuthorsDTO.getId()) || isInvalidBook(bookWithAuthorsDTO)) {
            throw new WrongEntityException("Wrong book in save method");
        }
        Book book = bookService.save(bookMapper.convertToEntity(bookWithAuthorsDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(bookMapper.convertToDto(book));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookWithAuthorsDTO> update(@PathVariable BigInteger id, @RequestBody BookWithAuthorsDTO bookWithAuthorsDTO) {
        log.debug("Enter into update method of BookController with input value: [{}]", bookWithAuthorsDTO);
        if (!Objects.equals(id, bookWithAuthorsDTO.getId())) {
            throw new EntityNotFoundException("Book id not equals provided id");
        }
        if (isInvalidBook(bookWithAuthorsDTO)) {
            throw new WrongEntityException("Wrong book in update method");
        }
        bookService.update(bookMapper.convertToEntity(bookWithAuthorsDTO));
        return ResponseEntity.status(HttpStatus.OK).body(bookWithAuthorsDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable BigInteger id) {
        log.debug("Enter into delete method of BookController with input value: [{}]", id);
        bookService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Book was deleted");
    }

    @GetMapping("/{id}/detail")
    public ResponseEntity<BookInfoDTO> getBookInfo(@PathVariable BigInteger id) {
        log.debug("Enter into getFullInfo method of BookController with input value: {}", id);
        BookInfoDTO bookDTO = bookMapper.convertToBookInfoDto(bookService.getBookWithAuthors(id));
        bookDTO.setReviews(reviewMapper.convertToDtoList(reviewService.getReviewsByBookId(id)));
        return ResponseEntity.status(HttpStatus.OK).body(bookDTO);
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookWithAuthorsDTO>> searchByName(@RequestParam String name) {
        log.debug("Enter into searchByName method of BookController");
        if (StringUtils.isBlank(name)) {
            throw new WrongInputValueException("Wrong input string for search");
        }
        List<BookWithAuthorsDTO> bookDTOS = bookMapper.convertToDtoListWithAuthors(bookService.getBooksByName(name));
        return ResponseEntity.status(HttpStatus.OK).body(bookDTOS);
    }

    @GetMapping("/rating")
    public ResponseEntity<List<BookDTO>> searchByRating(@RequestParam Integer rating) {
        log.debug("Enter into searchByRating method of BookController");
        if (Objects.isNull(rating) || rating <= 0 || rating > 6) {
            throw new WrongInputValueException("Wrong input rating for search");
        }
        List<BookDTO> bookDTOS = bookMapper.convertToDtoList(bookService.getBooksByRating(rating));
        return ResponseEntity.status(HttpStatus.OK).body(bookDTOS);
    }

    private boolean isInvalidBook(BookWithAuthorsDTO bookDTO) {
        return Objects.isNull(bookDTO) || StringUtils.isBlank(bookDTO.getName())
                || Objects.isNull(bookDTO.getIsbn())
                || CollectionUtils.isEmpty(bookDTO.getAuthors())
                || bookDTO.getYearPublisher() < 0
                || bookDTO.getYearPublisher() > LocalDate.now().getYear();
    }
}
