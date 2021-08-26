package com.softserve.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
@NoArgsConstructor
public class AuthorDTO {

    private BigInteger id;
    private String firstName;
    private String secondName;
}
