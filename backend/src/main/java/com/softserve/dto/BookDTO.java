package com.softserve.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BookDTO {

    private BigInteger id;
    private String name;
    private Integer yearPublisher;
    private BigInteger isbn;
    private String publisher;
    private BigDecimal rating;
    private List<AuthorDTO> authors;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss[.SSS][.SS][.S]")
    private LocalDateTime createdDate = LocalDateTime.now(); // todo: do you really need this field here?
}
