package com.softserve.utils;

import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
public class BookFilterParameters {

    private String name;
    private Integer yearPublisher;
    private BigInteger isbn;
    private String publisher;
    private Integer fromRating;
    private Integer toRating;
    private String searchingName;
    private BigInteger authorId;
}
