package com.softserve.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@Getter
@Setter
public class BookDTO {

    private BigInteger id;
    private String name;
    private Integer yearPublisher;
    private BigInteger isbn;
    private String publisher;
    private BigDecimal rating;
    private List<AuthorDTO> authors;
}
