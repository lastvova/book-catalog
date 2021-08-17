package com.softserve.controller;

import com.softserve.dto.BookDTO;
import com.softserve.dto.SaveBookDTO;
import com.softserve.entity.Book;
import com.softserve.mapper.BookMapper;
import com.softserve.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        BookDTO bookDTO = bookMapper.convertToDto(bookService.findById(id));
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
        Book book = bookService.update(bookMapper.convertToEntity(bookDTO));
        return ResponseEntity.status(HttpStatus.OK).body(bookMapper.convertToDto(book));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BookDTO> delete(@PathVariable BigInteger id) {
        BookDTO bookDTO = bookMapper.convertToDto(bookService.delete(id));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(bookDTO);
    }
}
