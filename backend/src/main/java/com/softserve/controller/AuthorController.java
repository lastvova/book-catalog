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

    //TODO should be GetMapping, RequestMapping is already defined on the class level
    @RequestMapping("")
    public ResponseEntity<Page<AuthorDTO>> getAll(@RequestParam(required = false) String sortBy,
                                                  @RequestParam(required = false) String order,
                                                  @RequestParam Integer page,
                                                  @RequestParam Integer size,
                                                  @RequestParam(required = false) String filterBy,
                                                  @RequestParam(required = false) String filterValue) {
        //TODO LOGGER is already configured for the current class
        LOGGER.debug("{}.getAll()", this.getClass().getName());
        Page<Author> result = authorService.getAll(setPageParameters(sortBy, order, page, size),
                setFilterParameters(filterBy, filterValue));
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

    //TODO no need for ("")
    @PostMapping("")
    public ResponseEntity<AuthorDTO> create(@RequestBody AuthorDTO authorDTO) {
        LOGGER.debug("{}.create({})", this.getClass().getName(), authorDTO);
        //TODO !Objects.isNull(authorDTO.getId()) could be extracted to the isInvalidAuthor method, Objects.nonNull() could be used instead
        if (!Objects.isNull(authorDTO.getId()) || isInvalidAuthor(authorDTO)) {
            throw new WrongEntityException("Wrong author in save method ");
        }
        Author author = authorService.create(authorMapper.convertToEntity(authorDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(authorMapper.convertToDto(author));
    }

    //TODO redundant id parameter, authorDTO already comprises the ID
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

    //TODO no need for (""), this action is not currently working
    @DeleteMapping("")
    public ResponseEntity<String> bulkDelete(@RequestParam List<BigInteger> ids) {
        LOGGER.debug("{}.bulkDelete()", this.getClass().getName());
        if (CollectionUtils.isEmpty(ids)) {
            throw new IllegalStateException("Empty collection with ids");
        }
        authorService.delete(null);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("authors was deleted");
    }

    //TODO could be replaced by getAll using filters
    @GetMapping("/{id}/books")
    public ResponseEntity<List<BookDTO>> getBooksByAuthor(@PathVariable BigInteger id) {
        LOGGER.debug("{}.getBooksByAuthor({})", this.getClass().getName(), id);
        List<BookDTO> bookDTOS = bookMapper.convertToDtoListWithAuthors(authorService.getBooksByAuthorId(id));
        return ResponseEntity.status(HttpStatus.OK).body(bookDTOS);
    }

    private boolean isInvalidAuthor(AuthorDTO authorDTO) {
        return Objects.isNull(authorDTO) || StringUtils.isBlank(authorDTO.getFirstName());
    }

    //TODO this logic must be extracted to an abstract controller, so that all existing controllers extend it
    private PaginationAndSortingParameters setPageParameters(String sortBy, String order, Integer page, Integer size) {
        PaginationAndSortingParameters paginationAndSortingParameters = new PaginationAndSortingParameters();
        paginationAndSortingParameters.setPageSize(size);
        paginationAndSortingParameters.setPageNumber(page);
        //TODO keep pagination and sorting parameters apart, create a separate class (POJO)
        if (Objects.nonNull(order)) {
            paginationAndSortingParameters.setSortDirection(Sort.Direction.fromString(order));
        }
        if (Objects.nonNull(sortBy)) {
            paginationAndSortingParameters.setSortBy(sortBy);
        }
        return paginationAndSortingParameters;
    }

    //TODO this logic must be extracted to an abstract controller, so that all existing controllers extend it
    private FilteringParameters setFilterParameters(String filterBy, String filterValue) {
        FilteringParameters filteringParameters = new FilteringParameters();
        if (Objects.nonNull(filterBy)) {
            filteringParameters.setFilterBy(EntityFields.valueOf(filterBy));
        }
        //TODO if this input parameter is coming as null, the filterValue class variable will be null as well, hence, what's the idea behind this logic?
        if (Objects.nonNull(filterValue)) {
            filteringParameters.setFilterValue(filterValue);
        }
        return filteringParameters;
    }
}
