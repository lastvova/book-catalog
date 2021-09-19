package com.softserve.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class SortingParameters {
    private Sort.Direction sortDirection = Sort.Direction.DESC;
    private String sortBy = "rating";
}
