package com.softserve.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorFilterParameters {

    private String name;
    private Integer fromRating;
    private Integer toRating;
}
