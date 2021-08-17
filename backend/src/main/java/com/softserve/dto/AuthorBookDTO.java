package com.softserve.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AuthorBookDTO {

    private AuthorDTO author;
    private SaveBookDTO book;
//    TODO realize this or delete
}
