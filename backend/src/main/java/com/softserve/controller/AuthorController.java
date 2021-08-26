package com.softserve.controller;

import com.softserve.dto.AuthorDTO;
import com.softserve.dto.BookDTO;
import com.softserve.entity.Author;
import com.softserve.exception.EntityNotFoundException;
import com.softserve.exception.WrongEntityException;
import com.softserve.mapper.AuthorMapper;
import com.softserve.mapper.BookMapper;
import com.softserve.service.AuthorService;
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
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/authors")
public class AuthorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorController.class);
    private final AuthorService service;
    private final AuthorMapper authorMapper;
    private final BookMapper bookMapper;

    @Autowired
    public AuthorController(AuthorService service, AuthorMapper mapper, BookMapper bookMapper) {
        this.service = service;
        this.authorMapper = mapper;
        this.bookMapper = bookMapper;
    }

    @GetMapping("")
    public ResponseEntity<List<AuthorDTO>> getAll() {
        LOGGER.debug("{}.getAll()", this.getClass().getName());
        List<AuthorDTO> authors = authorMapper.convertToDtoList(service.getAll());
        return ResponseEntity.status(HttpStatus.OK).body(authors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> get(@PathVariable BigInteger id) {
        LOGGER.debug("{}.get({})", this.getClass().getName(), id);
        AuthorDTO authorDTO = authorMapper.convertToDto(service.getById(id));
        return ResponseEntity.status(HttpStatus.OK).body(authorDTO);
    }

    @PostMapping("")
    public ResponseEntity<AuthorDTO> create(@RequestBody AuthorDTO authorDTO) {
        LOGGER.debug("{}.create({})", this.getClass().getName(), authorDTO);
        if (!Objects.isNull(authorDTO.getId()) || isInvalidAuthor(authorDTO)) {
            throw new WrongEntityException("Wrong author in save method ");
        }
        Author author = service.create(authorMapper.convertToEntity(authorDTO));
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
        service.update(authorMapper.convertToEntity(authorDTO));
        return ResponseEntity.status(HttpStatus.OK).body(authorDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable BigInteger id) {
        LOGGER.debug("{}.delete({})", this.getClass().getName(), id);
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("author was delete");
    }

    @GetMapping("/{id}/books")
    public ResponseEntity<List<BookDTO>> getBooksByAuthor(@PathVariable BigInteger id) {
        LOGGER.debug("{}.getBooksByAuthor({})", this.getClass().getName(), id);
        List<BookDTO> bookDTOS = bookMapper.convertToDtoListWithAuthors(service.getBooksByAuthorId(id));
        return ResponseEntity.status(HttpStatus.OK).body(bookDTOS);
    }

    private boolean isInvalidAuthor(AuthorDTO authorDTO) {
        return Objects.isNull(authorDTO) || StringUtils.isBlank(authorDTO.getFirstName());
    }
}
