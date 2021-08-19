package com.softserve.mapper;

import com.softserve.dto.BookDTO;
import com.softserve.dto.BookWithReviewsDTO;
import com.softserve.dto.BookWithAuthorsDTO;
import com.softserve.entity.Book;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

    Book convertToEntity(BookDTO bookDTO);

    Book convertToEntity(BookWithAuthorsDTO bookWithAuthorsDTO);

    Book convertToEntity(BookWithReviewsDTO bookWithReviewsDTO);

    BookDTO convertToDto(Book book);

    BookWithReviewsDTO convertToBookWithReviewsDTO(Book book);

    List<Book> convertToEntityList(List<BookDTO> books);

    List<BookDTO> convertToDtoList(List<Book> books);

    List<BookWithReviewsDTO> convertToDtoListWithReview(List<Book> books);
}
