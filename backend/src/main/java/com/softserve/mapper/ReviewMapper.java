package com.softserve.mapper;

import com.softserve.dto.ReviewDTO;
import com.softserve.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mapping(target = "book", source = "book")
    Review convertToEntity(ReviewDTO reviewDTO);

    @Mapping(target = "book.authors", source = "book.authors", ignore = true)
    ReviewDTO convertToDto(Review review);

    List<ReviewDTO> convertToDtoList(List<Review> reviews);
}
