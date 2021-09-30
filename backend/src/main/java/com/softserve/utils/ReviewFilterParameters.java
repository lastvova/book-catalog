package com.softserve.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewFilterParameters {
    private String commenterName;
    private String comment;
    private Integer fromRating;
    private Integer toRating;
    private String bookName;
}
