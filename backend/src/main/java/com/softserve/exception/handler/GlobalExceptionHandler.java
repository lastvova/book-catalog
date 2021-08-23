package com.softserve.exception.handler;

import com.softserve.exception.DeleteAuthorWithBooksException;
import com.softserve.exception.EntityNotFoundException;
import com.softserve.exception.WrongEntityException;
import com.softserve.exception.WrongInputValueException;
import com.softserve.exception.errorinfo.ErrorInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(DeleteAuthorWithBooksException.class)
    public ResponseEntity<ErrorInfo> deleteAuthorWithBooksExceptionHandler(
            HttpServletRequest request,
            DeleteAuthorWithBooksException exception) {
        ErrorInfo errorInfo = new ErrorInfo(HttpStatus.BAD_REQUEST);
        errorInfo.setUrl(request.getRequestURL().toString());
        errorInfo.setErrorMessage(exception.getMessage());
        log.error(exception.getMessage());
        return ResponseEntity.status(errorInfo.getStatus()).body(errorInfo);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorInfo> entityNotFoundExceptionHandler(
            HttpServletRequest request,
            EntityNotFoundException exception){
        ErrorInfo errorInfo = new ErrorInfo(HttpStatus.NOT_FOUND);
        errorInfo.setUrl(request.getRequestURL().toString());
        errorInfo.setErrorMessage(exception.getMessage());
        log.error(exception.getMessage());
        return ResponseEntity.status(errorInfo.getStatus()).body(errorInfo);
    }

    @ExceptionHandler(WrongEntityException.class)
    public ResponseEntity<ErrorInfo> entityNotFoundExceptionHandler(
            HttpServletRequest request,
            WrongEntityException exception){
        ErrorInfo errorInfo = new ErrorInfo(HttpStatus.BAD_REQUEST);
        errorInfo.setUrl(request.getRequestURL().toString());
        errorInfo.setErrorMessage(exception.getMessage());
        log.error(exception.getMessage());
        return ResponseEntity.status(errorInfo.getStatus()).body(errorInfo);
    }

    @ExceptionHandler(WrongInputValueException.class)
    public ResponseEntity<ErrorInfo> entityNotFoundExceptionHandler(
            HttpServletRequest request,
            WrongInputValueException exception){
        ErrorInfo errorInfo = new ErrorInfo(HttpStatus.BAD_REQUEST);
        errorInfo.setUrl(request.getRequestURL().toString());
        errorInfo.setErrorMessage(exception.getMessage());
        log.error(exception.getMessage());
        return ResponseEntity.status(errorInfo.getStatus()).body(errorInfo);
    }
}
