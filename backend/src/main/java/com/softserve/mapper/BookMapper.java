package com.softserve.mapper;

import com.softserve.dto.BookDTO;
import com.softserve.entity.Book;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

    Book convertToEntity(BookDTO bookDTO);

    BookDTO convertToDto(Book book);

    //TODO isn't WithAuthors part odd here? convertToDto also returns authors, doesn't it?
    List<BookDTO> convertToDtoListWithAuthors(List<Book> books);
}
