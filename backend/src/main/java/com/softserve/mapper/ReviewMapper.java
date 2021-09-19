package com.softserve.mapper;

import com.softserve.dto.ReviewDTO;
import com.softserve.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mapping(target = "book", source = "bookDTO")
    Review convertToEntity(ReviewDTO reviewDTO);

    ReviewDTO convertToDto(Review review);

    //TODO unused method
    List<Review> convertToEntityList(List<ReviewDTO> reviews);

    List<ReviewDTO> convertToDtoList(List<Review> reviews);
}
