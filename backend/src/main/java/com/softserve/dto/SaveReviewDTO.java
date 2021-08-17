package com.softserve.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class SaveReviewDTO {
    private String commenterName;
    private String comment;
    private Integer rating;
    private BookDTO bookDTO;
    private LocalDateTime createDate = LocalDateTime.now();
}
