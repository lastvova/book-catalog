package com.softserve.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListParams<P> {

//Default sorting could be by average rating
    String sortField = "rating";
    String order = "ASC";
    Integer pageNumber = 0;
    Integer pageSize = 5;
    P pattern;
}
