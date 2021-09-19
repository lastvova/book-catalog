package com.softserve.controller;

import com.softserve.dto.ReviewDTO;
import com.softserve.entity.Review;
import com.softserve.exception.WrongEntityException;
import com.softserve.mapper.ReviewMapper;
import com.softserve.service.ReviewService;
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
import java.util.Objects;

@RestController
@RequestMapping(value = "/api/reviews")
public class ReviewController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewController.class);
    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;
    private Boolean isMethodCreate = false;

    @Autowired
    public ReviewController(ReviewService service, ReviewMapper mapper) {
        this.reviewService = service;
        this.reviewMapper = mapper;
    }

    @GetMapping
    public ResponseEntity<Page<ReviewDTO>> getAll(@RequestParam(required = false) String sortBy,
                                                  @RequestParam(required = false) String order,
                                                  @RequestParam Integer page,
                                                  @RequestParam Integer size,
                                                  @RequestParam(required = false) String filterBy,
                                                  @RequestParam(required = false) String filterValue) {
        LOGGER.debug("getAll(page= {}, size={}, sortBy={}, order={}, filterBy={}, filterValue={})",
                page, size, sortBy, order, filterBy, filterValue);
        Page<Review> result = reviewService.getAll(setPageParameters(page, size), setSortParameters(sortBy, order),
                setFilterParameters(filterBy, filterValue));
        List<ReviewDTO> dtos = reviewMapper.convertToDtoList(result.getContent());
        Page<ReviewDTO> finalResult = new PageImpl<>(dtos, result.getPageable(), result.getTotalElements());
        return ResponseEntity.status(HttpStatus.OK).body(finalResult);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDTO> get(@PathVariable BigInteger id) {
        LOGGER.debug("get({})", id);
        ReviewDTO reviewDTO = reviewMapper.convertToDto(reviewService.getById(id));
        return ResponseEntity.status(HttpStatus.OK).body(reviewDTO);
    }

    @PostMapping
    public ResponseEntity<ReviewDTO> create(@RequestBody ReviewDTO reviewDTO) {
        LOGGER.debug("create({})", reviewDTO);
        isMethodCreate = true;
        if (isInvalidAuthor(reviewDTO)) {
            throw new WrongEntityException("Wrong review in save method ");
        }
        isMethodCreate = false;
        Review review = reviewService.create(reviewMapper.convertToEntity(reviewDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewMapper.convertToDto(review));
    }

    @PutMapping
    public ResponseEntity<ReviewDTO> update(@RequestBody ReviewDTO reviewDTO) {
        LOGGER.debug("update(dto = {})", reviewDTO);
        if (isInvalidAuthor(reviewDTO)) {
            throw new WrongEntityException("Wrong review in update method ");
        }
        Review review = reviewMapper.convertToEntity(reviewDTO);
        reviewService.update(review);
        return ResponseEntity.status(HttpStatus.OK).body(reviewDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable BigInteger id) {
        LOGGER.debug("delete({})", id);
        reviewService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Review was deleted");
    }

    private boolean isInvalidAuthor(ReviewDTO reviewDTO) {
        if (isMethodCreate) {
            return Objects.isNull(reviewDTO) || Objects.nonNull(reviewDTO.getId()) || StringUtils.isBlank(reviewDTO.getCommenterName())
                    || StringUtils.isBlank(reviewDTO.getComment());
        }
        return Objects.isNull(reviewDTO) || StringUtils.isBlank(reviewDTO.getCommenterName())
                || StringUtils.isBlank(reviewDTO.getComment());
    }
}
