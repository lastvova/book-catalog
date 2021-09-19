package com.softserve.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;

@Getter
@Setter
public class AuthorDTO {

    private BigInteger id;
    private String firstName;
    private String secondName;
    private BigDecimal rating;
}
