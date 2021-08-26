package com.softserve.controller;

import com.softserve.dto.ReviewDTO;
import com.softserve.entity.Review;
import com.softserve.exception.EntityNotFoundException;
import com.softserve.exception.WrongEntityException;
import com.softserve.mapper.ReviewMapper;
import com.softserve.service.ReviewService;
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
@RequestMapping(value = "/reviews")
public class ReviewController {

    private static final Logger log = LoggerFactory.getLogger(ReviewController.class);
    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;

    @Autowired
    public ReviewController(ReviewService service, ReviewMapper mapper) {
        this.reviewService = service;
        this.reviewMapper = mapper;
    }

    @GetMapping("")
    public ResponseEntity<List<ReviewDTO>> getAll() {
        log.debug("Enter into getAll method of ReviewController");
        List<ReviewDTO> reviewDTOS = reviewMapper.convertToDtoList(reviewService.getAll());
        return ResponseEntity.status(HttpStatus.OK).body(reviewDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDTO> get(@PathVariable BigInteger id) {
        log.debug("Enter into get method of ReviewController with input value: {}", id);
        ReviewDTO reviewDTO = reviewMapper.convertToDto(reviewService.getById(id));
        return ResponseEntity.status(HttpStatus.OK).body(reviewDTO);
    }

    @PostMapping("")
    public ResponseEntity<ReviewDTO> save(@RequestBody ReviewDTO reviewDTO) {
        log.debug("Enter into save method of ReviewController with input value: {}", reviewDTO);
        if (!Objects.isNull(reviewDTO.getId()) || isInvalidAuthor(reviewDTO)) {
            throw new WrongEntityException("Wrong review in save method ");
        }
        Review review = reviewService.create(reviewMapper.convertToEntity(reviewDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewMapper.convertToDto(review));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewDTO> update(@PathVariable BigInteger id, @RequestBody ReviewDTO reviewDTO) {
        log.debug("Enter into update method of ReviewController with input value: {}", reviewDTO);
        if (!Objects.equals(id, reviewDTO.getId())) {
            throw new EntityNotFoundException("Review id not equals provided id");
        }
        if (isInvalidAuthor(reviewDTO)) {
            throw new WrongEntityException("Wrong review in update method ");
        }
        Review review = reviewMapper.convertToEntity(reviewDTO);
        reviewService.update(review);
        return ResponseEntity.status(HttpStatus.OK).body(reviewDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable BigInteger id) {
        log.debug("Enter into delete method of ReviewController with input value: {}", id);
        reviewService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Review was deleted");
    }

    private boolean isInvalidAuthor(ReviewDTO reviewDTO) {
        return Objects.isNull(reviewDTO) || StringUtils.isBlank(reviewDTO.getCommenterName())
                || StringUtils.isBlank(reviewDTO.getComment());
    }
}
