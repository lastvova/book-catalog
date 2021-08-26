package com.softserve.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
@NoArgsConstructor
public class ReviewDTO {

    private BigInteger id;
    private String commenterName;
    private String comment;
    private Integer rating;
    private BookDTO bookDTO;
}
