package com.softserve.controller;

import com.softserve.dto.AuthorDTO;
import com.softserve.entity.Author;
import com.softserve.exception.WrongEntityException;
import com.softserve.mapper.AuthorMapper;
import com.softserve.service.AuthorService;
import com.softserve.utils.AuthorFilterParameters;
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
import java.util.List;

@RestController
@RequestMapping(value = "/api/authors")
public class AuthorController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorController.class);
    private final AuthorService authorService;
    private final AuthorMapper authorMapper;

    @Autowired
    public AuthorController(AuthorService authorService, AuthorMapper mapper) {
        this.authorService = authorService;
        this.authorMapper = mapper;
    }

    @PostMapping
    public ResponseEntity<Page<AuthorDTO>> getAllWithParameters(@RequestBody ListParams<AuthorFilterParameters> pageSortFilterParameters) {
        LOGGER.debug("getAllWithParameters( params = {})", pageSortFilterParameters);
        Page<Author> result = authorService.getAll(super.validatePageAndSortParameters(pageSortFilterParameters));
        List<AuthorDTO> dtos = authorMapper.convertToDtoList(result.getContent());
        Page<AuthorDTO> finalResult = new PageImpl<>(dtos, result.getPageable(), result.getTotalElements());
        return ResponseEntity.status(HttpStatus.OK).body(finalResult);
    }

    @GetMapping
    public ResponseEntity<Page<AuthorDTO>> getAll() {
        LOGGER.debug("getAll()");
        Page<Author> result = authorService.getAll(super.validatePageAndSortParameters(new ListParams<>()));
        List<AuthorDTO> dtos = authorMapper.convertToDtoList(result.getContent());
        Page<AuthorDTO> finalResult = new PageImpl<>(dtos, result.getPageable(), result.getTotalElements());
        return ResponseEntity.status(HttpStatus.OK).body(finalResult);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> get(@PathVariable BigInteger id) {
        LOGGER.debug("get({})", id);
        AuthorDTO authorDTO = authorMapper.convertToDto(authorService.getById(id));
        return ResponseEntity.status(HttpStatus.OK).body(authorDTO);
    }

    @PostMapping("/create")
    public ResponseEntity<AuthorDTO> create(@RequestBody AuthorDTO authorDTO) {
        LOGGER.debug("create({})", authorDTO);
        if (isInvalidAuthor(authorDTO, true)) {
            throw new WrongEntityException("Wrong author in save method ");
        }
        Author author = authorService.create(authorMapper.convertToEntity(authorDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(authorMapper.convertToDto(author));
    }

    @PutMapping
    public ResponseEntity<AuthorDTO> update(@RequestBody AuthorDTO authorDTO) {
        LOGGER.debug("update(dto = {})", authorDTO);
        if (isInvalidAuthor(authorDTO, false)) {
            throw new WrongEntityException("Wrong author in update method ");
        }
        authorService.update(authorMapper.convertToEntity(authorDTO));
        return ResponseEntity.status(HttpStatus.OK).body(authorDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable BigInteger id) {
        LOGGER.debug("delete({})", id);
        authorService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("author was deleted");
    }

    @DeleteMapping
    public ResponseEntity<String> bulkDelete(@RequestParam List<BigInteger> ids) {
        LOGGER.debug("bulkDelete()");
        if (CollectionUtils.isEmpty(ids)) {
            throw new IllegalArgumentException("Empty collection with ids");
        }
        authorService.bulkDelete(ids);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("authors was deleted");
    }

    private boolean isInvalidAuthor(AuthorDTO authorDTO, boolean isMethodCreate) {
        if (isMethodCreate) {
            return authorDTO == null || authorDTO.getId() != null || StringUtils.isBlank(authorDTO.getFirstName());
        }
        return authorDTO == null || StringUtils.isBlank(authorDTO.getFirstName());
    }
}
