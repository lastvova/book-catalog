package com.softserve.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SaveBookDTO {

    private String name;
    private Integer yearPublisher;
    private BigInteger isbn;
    private String publisher;
    private List<AuthorDTO> authors;
    private LocalDateTime createdDate = LocalDateTime.now();
}
