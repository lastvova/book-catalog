package com.softserve.controller;

import com.softserve.dto.AuthorDTO;
import com.softserve.dto.BookDTO;
import com.softserve.entity.Author;
import com.softserve.enums.EntityFields;
import com.softserve.exception.EntityNotFoundException;
import com.softserve.exception.WrongEntityException;
import com.softserve.mapper.AuthorMapper;
import com.softserve.mapper.BookMapper;
import com.softserve.service.AuthorService;
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

    @RequestMapping("")
    public ResponseEntity<Page<AuthorDTO>> getAll(@RequestParam(required = false, defaultValue = "createdDate") String sortBy,
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
        Page<Author> result = authorService.getAll(paginationAndSortingParameters, filteringParameters);
        List<AuthorDTO> dtos = authorMapper.convertToDtoList(result.getContent());
        Page<AuthorDTO> finalResult = new PageImpl<>(dtos, result.getPageable(), result.getTotalElements());
        return ResponseEntity.status(HttpStatus.OK).body(finalResult);
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

}
