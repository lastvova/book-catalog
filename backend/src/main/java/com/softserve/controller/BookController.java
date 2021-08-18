package com.softserve.controller;

import com.softserve.dto.BookDTO;
import com.softserve.dto.SaveBookDTO;
import com.softserve.entity.Book;
import com.softserve.mapper.BookMapper;
import com.softserve.service.BookService;
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
@RequestMapping(value = "/books")
public class BookController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    @Autowired
    public BookController(BookService service, BookMapper mapper) {
        this.bookService = service;
        this.bookMapper = mapper;
    }

    @GetMapping("")
    public ResponseEntity<List<BookDTO>> getAll() {
        List<BookDTO> bookDTOS = bookMapper.convertToDtoList(bookService.getAll());
        return ResponseEntity.status(HttpStatus.OK).body(bookDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> get(@PathVariable BigInteger id) {
        BookDTO bookDTO = bookMapper.convertToDto(bookService.getById(id));
        return ResponseEntity.status(HttpStatus.OK).body(bookDTO);
    }

    //TODO save with authors
    @PostMapping("")
    public ResponseEntity<BookDTO> save(@RequestBody SaveBookDTO saveBookDTO) {
        Book book = bookMapper.convertToEntity(saveBookDTO);
        book = bookService.save(bookMapper.convertToEntity(saveBookDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(bookMapper.convertToDto(book));
    }

    //    TODO when update, dont delete reviews
    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> update(@PathVariable BigInteger id, @RequestBody BookDTO bookDTO) {
        if (!Objects.equals(id, bookDTO.getId())) {
            throw new IllegalStateException("Invalid entity or id");
        }
        bookService.update(bookMapper.convertToEntity(bookDTO));
        return ResponseEntity.status(HttpStatus.OK).body(bookDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BookDTO> delete(@PathVariable BigInteger id) {
        BookDTO bookDTO = bookMapper.convertToDto(bookService.getById(id));
        bookService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(bookDTO);
    }
}
