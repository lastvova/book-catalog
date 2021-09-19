package com.softserve.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
public class ReviewDTO {

    private BigInteger id;
    private String commenterName;
    private String comment;
    private Integer rating;
    private BookDTO bookDTO;
}
