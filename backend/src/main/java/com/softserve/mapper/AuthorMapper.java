package com.softserve.mapper;

import com.softserve.dto.AuthorDTO;
import com.softserve.entity.Author;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    Author convertToEntity(AuthorDTO authorDTO);

    AuthorDTO convertToDto(Author author);

    List<Author> convertToEntityList(List<AuthorDTO> authors);

    List<AuthorDTO> convertToDtoList(List<Author> authors);
}
