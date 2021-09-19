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
    //TODO try to avoid DTO suffix in class variables, on the other side it's okay to use it with local variables
    private BookDTO bookDTO;
}
