package com.softserve.exception.errorinfo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ErrorInfo {

    private HttpStatus status;
    private String url;
    private String message;

    public ErrorInfo(HttpStatus status) {
        this.status = status;
    }
}
