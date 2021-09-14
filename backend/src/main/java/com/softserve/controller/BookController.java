package com.softserve.controller;

import com.softserve.dto.BookDTO;
import com.softserve.entity.Book;
import com.softserve.exception.EntityNotFoundException;
import com.softserve.exception.WrongEntityException;
import com.softserve.mapper.BookMapper;
import com.softserve.service.BookService;
import com.softserve.util.FilteringParameters;
import com.softserve.util.OutputSql;
import com.softserve.util.PaginationParameters;
import com.softserve.util.SearchResult;
import com.softserve.util.SortingParameters;
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
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/api/books")
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
    public ResponseEntity<SearchResult<BookDTO>> getAllWithParameters(@RequestParam(required = false) String sortingField,
                                                                      @RequestParam(required = false) String sortingOrder,
                                                                      @RequestParam(required = false, defaultValue = "1") Integer currentPage,
                                                                      @RequestParam(required = false, defaultValue = "5") Integer recordsPerPage) {
        LOGGER.debug("{}.getALl()", this.getClass().getName());
        OutputSql params = new OutputSql();
        params.setPaginationParams(new PaginationParameters(currentPage, recordsPerPage));
        params.setSortingParameters(new SortingParameters(sortingField, sortingOrder));
        return ResponseEntity.status(HttpStatus.OK).body(getResult(params));
    }

    @GetMapping("/filter")
    public ResponseEntity<SearchResult<BookDTO>> getAllWithFilterParameters(@RequestParam(required = false) String sortingField,
                                                                            @RequestParam(required = false) String sortingOrder,
                                                                            @RequestParam(required = false, defaultValue = "1") Integer currentPage,
                                                                            @RequestParam(required = false, defaultValue = "5") Integer recordsPerPage,
                                                                            @RequestParam String filteringField,
                                                                            @RequestParam String filteringOperator,
                                                                            @RequestParam String filteringValue) {
        LOGGER.debug("{}.getALl()", this.getClass().getName());
        OutputSql params = new OutputSql();
        params.setPaginationParams(new PaginationParameters(currentPage, recordsPerPage));
        params.setSortingParameters(new SortingParameters(sortingField, sortingOrder));
        params.setFilteringParams(Collections.singletonList(new FilteringParameters(filteringField, filteringValue, filteringOperator)));
        return ResponseEntity.status(HttpStatus.OK).body(getResult(params));
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

    @DeleteMapping("")
    public ResponseEntity<String> bulkDelete(@RequestParam List<BigInteger> ids) {
        LOGGER.debug("{}.bulkDelete()", this.getClass().getName());
        if (CollectionUtils.isEmpty(ids)) {
            throw new IllegalStateException("Empty collection with ids");
        }
        bookService.delete(null);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("authors was deleted");
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

    private SearchResult<BookDTO> getResult(OutputSql params) {
        SearchResult<Book> resultFromService = bookService.getAll(params);
        List<BookDTO> books = bookMapper.convertToDtoListWithAuthors(resultFromService.getData());
        return new SearchResult<>(resultFromService.getTotalRecords(), books);
    }
}
