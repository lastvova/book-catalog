package com.softserve.utils;

import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
public class ReviewFilterParameters {
    private String commenterName;
    private String comment;
    private Integer fromRating;
    private Integer toRating;
    private String bookName;
    private BigInteger bookId;
}
