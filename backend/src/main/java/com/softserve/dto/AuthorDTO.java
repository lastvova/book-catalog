package com.softserve.dto;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.BigInteger;

@Getter
@Setter
public class AuthorDTO implements Comparable<AuthorDTO> {

    private BigInteger id;
    private String firstName;
    private String secondName;
    private BigDecimal rating;

    @Override
    public int compareTo(@NotNull AuthorDTO o) {
        return this.firstName.compareTo(o.firstName);
    }
}
