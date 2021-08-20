package com.softserve.mapper;

import com.softserve.dto.BookDTO;
import com.softserve.dto.BookWithReviewsAndAuthorsDTO;
import com.softserve.dto.BookWithAuthorsDTO;
import com.softserve.entity.Book;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

    Book convertToEntity(BookDTO bookDTO);

    Book convertToEntity(BookWithAuthorsDTO bookWithAuthorsDTO);

    Book convertToEntity(BookWithReviewsAndAuthorsDTO bookWithReviewsAndAuthorsDTO);

    BookDTO convertToDto(Book book);

    BookWithReviewsAndAuthorsDTO convertToBookWithReviewsAndAuthorDTO(Book book);

    List<Book> convertToEntityList(List<BookDTO> books);

    List<BookDTO> convertToDtoList(List<Book> books);

    List<BookWithAuthorsDTO> convertToDtoListWithAuthors(List<Book> books);
}
