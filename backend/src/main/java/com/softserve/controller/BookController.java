package com.softserve.controller;

import com.softserve.dto.BookDTO;
import com.softserve.entity.Book;
import com.softserve.exception.WrongEntityException;
import com.softserve.mapper.BookMapper;
import com.softserve.service.BookService;
import com.softserve.utils.BookFilterParameters;
import com.softserve.utils.ListParams;
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

@RestController
@RequestMapping(value = "/api/books")
public class BookController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);
    private final BookService bookService;
    private final BookMapper bookMapper;

    @Autowired
    public BookController(BookService bookService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;

    }

    @PostMapping
    public ResponseEntity<Page<BookDTO>> getAllWithParameters(@RequestBody ListParams<BookFilterParameters> pageSortFilterParameters) {
        LOGGER.debug("getAllWithParameters( params = {})", pageSortFilterParameters);
        Page<Book> result = bookService.getAll(super.validatePageAndSortParameters(pageSortFilterParameters));
        List<BookDTO> dtos = bookMapper.convertToDtoList(result.getContent());
        Page<BookDTO> finalResult = new PageImpl<>(dtos, result.getPageable(), result.getTotalElements());
        return ResponseEntity.status(HttpStatus.OK).body(finalResult);
    }

    @GetMapping
    public ResponseEntity<Page<BookDTO>> getAll() {
        LOGGER.debug("getAll()");
        Page<Book> result = bookService.getAll(super.validatePageAndSortParameters(new ListParams<>()));
        List<BookDTO> dtos = bookMapper.convertToDtoList(result.getContent());
        Page<BookDTO> finalResult = new PageImpl<>(dtos, result.getPageable(), result.getTotalElements());
        return ResponseEntity.status(HttpStatus.OK).body(finalResult);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> get(@PathVariable BigInteger id) {
        LOGGER.debug("get({})", id);
        BookDTO bookDTO = bookMapper.convertToDto(bookService.getById(id));
        return ResponseEntity.status(HttpStatus.OK).body(bookDTO);
    }

    @PostMapping("/create")
    public ResponseEntity<BookDTO> create(@RequestBody BookDTO bookDTO) {
        LOGGER.debug("create({})", bookDTO);
        if (isInvalidBook(bookDTO, true)) {
            throw new WrongEntityException("Wrong book in save method");
        }
        Book book = bookService.create(bookMapper.convertToEntity(bookDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(bookMapper.convertToDto(book));
    }

    @PutMapping
    public ResponseEntity<BookDTO> update(@RequestBody BookDTO bookDTO) {
        LOGGER.debug("update(dto = {})", bookDTO);
        if (isInvalidBook(bookDTO, false)) {
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

    private boolean isInvalidBook(BookDTO bookDTO, boolean isMethodCreate) {
        if (isMethodCreate) {
            return bookDTO == null || bookDTO.getId() != null || StringUtils.isBlank(bookDTO.getName())
                    || bookDTO.getIsbn() == null
                    || CollectionUtils.isEmpty(bookDTO.getAuthors())
                    || isInValidYearOfPublisher(bookDTO);
        }
        return bookDTO == null || StringUtils.isBlank(bookDTO.getName())
                || bookDTO.getIsbn() == null
                || CollectionUtils.isEmpty(bookDTO.getAuthors())
                || isInValidYearOfPublisher(bookDTO);
    }

    private boolean isInValidYearOfPublisher(BookDTO bookDTO) {
        if (bookDTO.getYearPublisher() == null) {
            return false;
        }
        return bookDTO.getYearPublisher() < 0
                || bookDTO.getYearPublisher() > LocalDate.now().getYear();
    }
}
