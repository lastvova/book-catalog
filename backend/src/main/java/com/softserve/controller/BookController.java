package com.softserve.controller;

import com.softserve.dto.BookDTO;
import com.softserve.entity.Book;
import com.softserve.exception.WrongEntityException;
import com.softserve.mapper.BookMapper;
import com.softserve.service.BookService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
@RequestMapping(value = "/api/books")
public class BookController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);
    private final BookService bookService;
    private final BookMapper bookMapper;
    private boolean isMethodCreate = false;

    @Autowired
    public BookController(BookService bookService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;

    }

    @GetMapping
    public ResponseEntity<Page<BookDTO>> getAll(@RequestParam(required = false) String sortBy,
                                                @RequestParam(required = false) String order,
                                                @RequestParam Integer page,
                                                @RequestParam Integer size,
                                                @RequestParam(required = false) String filterBy,
                                                @RequestParam(required = false) String filterValue) {
        LOGGER.debug("getAll(page= {}, size={}, sortBy={}, order={}, filterBy={}, filterValue={})",
                page, size, sortBy, order, filterBy, filterValue);

        Page<Book> result = bookService.getAll(setPageParameters(page, size), setSortParameters(sortBy, order),
                setFilterParameters(filterBy, filterValue));
        List<BookDTO> dtos = bookMapper.convertToDtoListWithAuthors(result.getContent());
        Page<BookDTO> finalResult = new PageImpl<>(dtos, result.getPageable(), result.getTotalElements());
        return ResponseEntity.status(HttpStatus.OK).body(finalResult);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> get(@PathVariable BigInteger id) {
        LOGGER.debug("get({})", id);
        BookDTO bookDTO = bookMapper.convertToDto(bookService.getById(id));
        return ResponseEntity.status(HttpStatus.OK).body(bookDTO);
    }

    @PostMapping
    public ResponseEntity<BookDTO> create(@RequestBody BookDTO bookDTO) {
        LOGGER.debug("create({})", bookDTO);
        isMethodCreate = true;
        if (isInvalidBook(bookDTO)) {
            throw new WrongEntityException("Wrong book in save method");
        }
        isMethodCreate = false;
        Book book = bookService.create(bookMapper.convertToEntity(bookDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(bookMapper.convertToDto(book));
    }

    @PutMapping
    public ResponseEntity<BookDTO> update(@RequestBody BookDTO bookDTO) {
        LOGGER.debug("update(dto = {})", bookDTO);
        if (isInvalidBook(bookDTO)) {
            throw new WrongEntityException("Wrong book in update method");
        }
        bookService.update(bookMapper.convertToEntity(bookDTO));
        return ResponseEntity.status(HttpStatus.OK).body(bookDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable BigInteger id) {
        LOGGER.debug("delete({})", id);
        bookService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Book was deleted");
    }

    @DeleteMapping
    public ResponseEntity<String> bulkDelete(@RequestParam List<BigInteger> ids) {
        LOGGER.debug("bulkDelete({})", ids);
        if (CollectionUtils.isEmpty(ids)) {
            throw new IllegalStateException("Empty collection with ids");
        }
        bookService.delete(null);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("authors was deleted");
    }

    private boolean isInvalidBook(BookDTO bookDTO) {
        if (isMethodCreate) {
            return Objects.isNull(bookDTO) || Objects.nonNull(bookDTO.getId()) || StringUtils.isBlank(bookDTO.getName())
                    || Objects.isNull(bookDTO.getIsbn())
                    || CollectionUtils.isEmpty(bookDTO.getAuthors())
                    || isInValidYearOfPublisher(bookDTO);
        }
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
