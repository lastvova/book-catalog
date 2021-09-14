package com.softserve.controller;

import com.softserve.dto.ReviewDTO;
import com.softserve.entity.Review;
import com.softserve.exception.EntityNotFoundException;
import com.softserve.exception.WrongEntityException;
import com.softserve.mapper.ReviewMapper;
import com.softserve.service.ReviewService;
import com.softserve.util.OutputSql;
import com.softserve.util.PaginationParameters;
import com.softserve.util.SearchResult;
import com.softserve.util.SortingParameters;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
@RequestMapping(value = "/api/reviews")
public class ReviewController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewController.class);
    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;

    @Autowired
    public ReviewController(ReviewService service, ReviewMapper mapper) {
        this.reviewService = service;
        this.reviewMapper = mapper;
    }

    @GetMapping("")
    public ResponseEntity<SearchResult<ReviewDTO>> getAll(@ModelAttribute("paginationParameters") PaginationParameters paginationParameters,
                                                          @ModelAttribute("sortingParameters") SortingParameters sortingParameters) {
        OutputSql params = new OutputSql();
        params.setPaginationParams(paginationParameters);
        params.setSortingParameters(sortingParameters);
        LOGGER.debug("{}.getALl()", this.getClass().getName());
        return ResponseEntity.status(HttpStatus.OK).body(getResult(params));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDTO> get(@PathVariable BigInteger id) {
        LOGGER.debug("{}.get({})", this.getClass().getName(), id);
        ReviewDTO reviewDTO = reviewMapper.convertToDto(reviewService.getById(id));
        return ResponseEntity.status(HttpStatus.OK).body(reviewDTO);
    }

    @PostMapping("")
    public ResponseEntity<ReviewDTO> create(@RequestBody ReviewDTO reviewDTO) {
        LOGGER.debug("{}.create({})", this.getClass().getName(), reviewDTO);
        if (!Objects.isNull(reviewDTO.getId()) || isInvalidAuthor(reviewDTO)) {
            throw new WrongEntityException("Wrong review in save method ");
        }
        Review review = reviewService.create(reviewMapper.convertToEntity(reviewDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewMapper.convertToDto(review));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewDTO> update(@PathVariable BigInteger id, @RequestBody ReviewDTO reviewDTO) {
        LOGGER.debug("{}.update(id = {} dto = {})", this.getClass().getName(), id, reviewDTO);
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
        LOGGER.debug("{}.delete({})", this.getClass().getName(), id);
        reviewService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Review was deleted");
    }

    private boolean isInvalidAuthor(ReviewDTO reviewDTO) {
        return Objects.isNull(reviewDTO) || StringUtils.isBlank(reviewDTO.getCommenterName())
                || StringUtils.isBlank(reviewDTO.getComment());
    }

    private SearchResult<ReviewDTO> getResult(OutputSql params) {
        SearchResult<Review> resultFromService = reviewService.getAll(params);
        List<ReviewDTO> reviews = reviewMapper.convertToDtoList(resultFromService.getData());
        return new SearchResult<>(resultFromService.getTotalRecords(), reviews);
    }
}
