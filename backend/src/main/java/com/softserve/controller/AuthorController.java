package com.softserve.controller;

import com.softserve.dto.AuthorDTO;
import com.softserve.dto.BookDTO;
import com.softserve.entity.Author;
import com.softserve.exception.EntityNotFoundException;
import com.softserve.exception.WrongEntityException;
import com.softserve.mapper.AuthorMapper;
import com.softserve.mapper.BookMapper;
import com.softserve.service.AuthorService;
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
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/api/authors")
public class AuthorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorController.class);
    private final AuthorService authorService;
    private final AuthorMapper authorMapper;
    private final BookMapper bookMapper;

    @Autowired
    public AuthorController(AuthorService authorService, AuthorMapper mapper, BookMapper bookMapper) {
        this.authorService = authorService;
        this.authorMapper = mapper;
        this.bookMapper = bookMapper;
    }

    @GetMapping("")
    public ResponseEntity<SearchResult<AuthorDTO>> getAllWithParameters(@RequestParam(required = false) String sortingField,
                                                                        @RequestParam(required = false) String sortingOrder,
                                                                        @RequestParam(required = false, defaultValue = "1") Integer currentPage,
                                                                        @RequestParam(required = false, defaultValue = "5") Integer recordsPerPage) {
        LOGGER.debug("{}.getAll()", this.getClass().getName());
        OutputSql params = new OutputSql();
        params.setPaginationParams(new PaginationParameters(currentPage, recordsPerPage));
        params.setSortingParameters(new SortingParameters(sortingField, sortingOrder));
        return ResponseEntity.status(HttpStatus.OK).body(getResult(params));
    }

    @GetMapping("/filter")
    public ResponseEntity<SearchResult<AuthorDTO>> getAllWithFilterParameters(@RequestParam(required = false) String sortingField,
                                                                              @RequestParam(required = false) String sortingOrder,
                                                                              @RequestParam(required = false, defaultValue = "1") Integer currentPage,
                                                                              @RequestParam(required = false, defaultValue = "5") Integer recordsPerPage,
                                                                              @RequestParam String filteringField,
                                                                              @RequestParam String filteringOperator,
                                                                              @RequestParam String filteringValue) {
        LOGGER.debug("{}.getAll()", this.getClass().getName());
        OutputSql params = new OutputSql();
        params.setPaginationParams(new PaginationParameters(currentPage, recordsPerPage));
        params.setSortingParameters(new SortingParameters(sortingField, sortingOrder));
        params.setFilteringParams(Collections.singletonList(new FilteringParameters(filteringField, filteringValue, filteringOperator)));
        return ResponseEntity.status(HttpStatus.OK).body(getResult(params));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> get(@PathVariable BigInteger id) {
        LOGGER.debug("{}.get({})", this.getClass().getName(), id);
        AuthorDTO authorDTO = authorMapper.convertToDto(authorService.getById(id));
        return ResponseEntity.status(HttpStatus.OK).body(authorDTO);
    }

    @PostMapping("")
    public ResponseEntity<AuthorDTO> create(@RequestBody AuthorDTO authorDTO) {
        LOGGER.debug("{}.create({})", this.getClass().getName(), authorDTO);
        if (!Objects.isNull(authorDTO.getId()) || isInvalidAuthor(authorDTO)) {
            throw new WrongEntityException("Wrong author in save method ");
        }
        Author author = authorService.create(authorMapper.convertToEntity(authorDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(authorMapper.convertToDto(author));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorDTO> update(@PathVariable BigInteger id, @RequestBody AuthorDTO authorDTO) {
        LOGGER.debug("{}.update(id = {} dto = {})", this.getClass().getName(), id, authorDTO);
        if (!Objects.equals(id, authorDTO.getId())) {
            throw new EntityNotFoundException("Author id not equals provided id");
        }
        if (isInvalidAuthor(authorDTO)) {
            throw new WrongEntityException("Wrong author in update method ");
        }
        authorService.update(authorMapper.convertToEntity(authorDTO));
        return ResponseEntity.status(HttpStatus.OK).body(authorDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable BigInteger id) {
        LOGGER.debug("{}.delete({})", this.getClass().getName(), id);
        authorService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("author was deleted");
    }

    @DeleteMapping("")
    public ResponseEntity<String> bulkDelete(@RequestParam List<BigInteger> ids) {
        LOGGER.debug("{}.bulkDelete()", this.getClass().getName());
        if (CollectionUtils.isEmpty(ids)) {
            throw new IllegalStateException("Empty collection with ids");
        }
        authorService.delete(null);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("authors was deleted");
    }

    @GetMapping("/{id}/books")
    public ResponseEntity<List<BookDTO>> getBooksByAuthor(@PathVariable BigInteger id) {
        LOGGER.debug("{}.getBooksByAuthor({})", this.getClass().getName(), id);
        List<BookDTO> bookDTOS = bookMapper.convertToDtoListWithAuthors(authorService.getBooksByAuthorId(id));
        return ResponseEntity.status(HttpStatus.OK).body(bookDTOS);
    }

    private boolean isInvalidAuthor(AuthorDTO authorDTO) {
        return Objects.isNull(authorDTO) || StringUtils.isBlank(authorDTO.getFirstName());
    }

    private SearchResult<AuthorDTO> getResult(OutputSql params) {
        SearchResult<Author> resultFromService = authorService.getAll(params);
        List<AuthorDTO> authors = authorMapper.convertToDtoList(resultFromService.getData());
        return new SearchResult<>(resultFromService.getTotalRecords(), authors);
    }
}
