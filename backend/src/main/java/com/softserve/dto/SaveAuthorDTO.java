package com.softserve.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class SaveAuthorDTO {

    private String firstName;
    private String secondName;
    private LocalDateTime createDate = LocalDateTime.now();
}
