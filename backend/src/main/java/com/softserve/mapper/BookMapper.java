package com.softserve.mapper;

import com.softserve.dto.BookDTO;
import com.softserve.entity.Book;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

    Book convertToEntity(BookDTO bookDTO);

    BookDTO convertToDto(Book book);

    List<BookDTO> convertToDtoList(List<Book> books);
}
