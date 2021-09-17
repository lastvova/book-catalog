package com.softserve.controller;

import com.softserve.dto.BookDTO;
import com.softserve.entity.Book;
import com.softserve.enums.EntityFields;
import com.softserve.exception.EntityNotFoundException;
import com.softserve.exception.WrongEntityException;
import com.softserve.mapper.BookMapper;
import com.softserve.service.BookService;
import com.softserve.util.FilteringParameters;
import com.softserve.util.PaginationAndSortingParameters;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
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
public class BookController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);
    private final BookService bookService;
    private final BookMapper bookMapper;

    @Autowired
    public BookController(BookService bookService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;

    }

    @RequestMapping("")
    public ResponseEntity<Page<BookDTO>> getAll(@RequestParam(required = false, defaultValue = "createdDate") String sortBy,
                                                @RequestParam(required = false, defaultValue = "ASC") String order,
                                                @RequestParam(required = false, defaultValue = "0") Integer page,
                                                @RequestParam(required = false, defaultValue = "5") Integer size,
                                                @RequestParam(required = false) String filterBy,
                                                @RequestParam(required = false) String filterValue) {
        LOGGER.debug("{}.getAll()", this.getClass().getName());
        PaginationAndSortingParameters paginationAndSortingParameters = new PaginationAndSortingParameters();
        paginationAndSortingParameters.setPageSize(size);
        paginationAndSortingParameters.setPageNumber(page);
        paginationAndSortingParameters.setSortDirection(Sort.Direction.fromString(order));
        paginationAndSortingParameters.setSortBy(sortBy);
        FilteringParameters filteringParameters = new FilteringParameters();
        if(Objects.nonNull(filterBy)){
            filteringParameters.setFilterBy(EntityFields.valueOf(filterBy));
        }
        filteringParameters.setFilterValue(filterValue);
        Page<Book> result = bookService.getAll(paginationAndSortingParameters, filteringParameters);
        List<BookDTO> dtos = bookMapper.convertToDtoListWithAuthors(result.getContent());
        Page<BookDTO> finalResult = new PageImpl<>(dtos, result.getPageable(), result.getTotalElements());
        return ResponseEntity.status(HttpStatus.OK).body(finalResult);
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
}
