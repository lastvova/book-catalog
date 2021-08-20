package com.softserve.mapper;

import com.softserve.dto.BookDTO;
import com.softserve.dto.BookInfoDTO;
import com.softserve.dto.BookWithAuthorsDTO;
import com.softserve.entity.Book;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

    Book convertToEntity(BookWithAuthorsDTO bookWithAuthorsDTO);

    BookDTO convertToDto(Book book);

    BookInfoDTO convertToBookInfoDto(Book book);

    List<BookDTO> convertToDtoList(List<Book> books);

    List<BookWithAuthorsDTO> convertToDtoListWithAuthors(List<Book> books);
}
