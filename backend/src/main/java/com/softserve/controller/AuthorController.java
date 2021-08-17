package com.softserve.controller;

import com.softserve.dto.AuthorDTO;
import com.softserve.dto.BookDTO;
import com.softserve.dto.SaveAuthorDTO;
import com.softserve.entity.Author;
import com.softserve.exception.IncorrectFieldException;
import com.softserve.mapper.AuthorMapper;
import com.softserve.mapper.BookMapper;
import com.softserve.service.AuthorService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/authors")
public class AuthorController {

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
        List<AuthorDTO> authors = authorMapper.convertToDtoList(service.getAll());
        return ResponseEntity.status(HttpStatus.OK).body(authors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> get(@PathVariable BigInteger id) {
        AuthorDTO authorDTO = authorMapper.convertToDto(service.findById(id));
        return ResponseEntity.status(HttpStatus.OK).body(authorDTO);
    }

    @PostMapping("")
    public ResponseEntity<AuthorDTO> save(@RequestBody SaveAuthorDTO saveAuthorDTO) {
        Author author = service.save(authorMapper.convertToEntity(saveAuthorDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(authorMapper.convertToDto(author));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorDTO> update(@PathVariable BigInteger id, @RequestBody AuthorDTO authorDTO) {
        if (!Objects.equals(id, authorDTO.getId())) {
            throw new IllegalStateException("Invalid entity or id");
        }
        Author author = authorMapper.convertToEntity(authorDTO);
        service.update(author);
        return ResponseEntity.status(HttpStatus.OK).body(authorDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AuthorDTO> delete(@PathVariable BigInteger id) {
        AuthorDTO authorDTO = authorMapper.convertToDto(service.delete(id));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(authorDTO);
    }

    @GetMapping("/{id}/books")
    public ResponseEntity<List<BookDTO>> getBooksByAuthor(@PathVariable BigInteger id) {
        List<BookDTO> bookDTOS = bookMapper.convertToDtoList(service.getBooksByAuthorId(id));
        return ResponseEntity.status(HttpStatus.OK).body(bookDTOS);
    }
}
