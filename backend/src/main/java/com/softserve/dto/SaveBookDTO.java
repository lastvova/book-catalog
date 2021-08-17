package com.softserve.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SaveBookDTO {

    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate yearPublisher;
    private BigInteger isbn;
    private String publisher;
    private LocalDateTime createDate = LocalDateTime.now();
}
