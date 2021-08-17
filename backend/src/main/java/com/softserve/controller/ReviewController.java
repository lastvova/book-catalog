package com.softserve.controller;

import com.softserve.dto.ReviewDTO;
import com.softserve.dto.SaveReviewDTO;
import com.softserve.entity.Review;
import com.softserve.exception.IncorrectFieldException;
import com.softserve.mapper.ReviewMapper;
import com.softserve.service.ReviewService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;

    @Autowired
    public ReviewController(ReviewService service, ReviewMapper mapper) {
        this.reviewService = service;
        this.reviewMapper = mapper;
    }

    @GetMapping("")
    public ResponseEntity<List<ReviewDTO>> getAll() {
        List<ReviewDTO> reviewDTOS = reviewMapper.convertToDtoList(reviewService.getAll());
        return ResponseEntity.status(HttpStatus.OK).body(reviewDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDTO> get(@PathVariable BigInteger id) {
        ReviewDTO reviewDTO = reviewMapper.convertToDto(reviewService.findById(id));
        return ResponseEntity.status(HttpStatus.OK).body(reviewDTO);
    }

    @PostMapping("")
    public ResponseEntity<ReviewDTO> save(@RequestBody SaveReviewDTO saveReviewDTO) {
        Review review = reviewMapper.convertToEntity(saveReviewDTO);
        reviewService.save(review);
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewMapper.convertToDto(review));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewDTO> update(@PathVariable BigInteger id, @RequestBody ReviewDTO reviewDTO) {
        if (!Objects.equals(id, reviewDTO.getId())) {
            throw new IllegalStateException("Invalid id");
        }
        Review review = reviewMapper.convertToEntity(reviewDTO);
        reviewService.update(review);
        return ResponseEntity.status(HttpStatus.OK).body(reviewDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ReviewDTO> delete(@PathVariable BigInteger id) {
        ReviewDTO reviewDTO = reviewMapper.convertToDto(reviewService.delete(id));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(reviewDTO);
    }


}
