package com.softserve.exception.handler;

import com.softserve.exception.DeleteAuthorWithBooksException;
import com.softserve.exception.EntityNotFoundException;
import com.softserve.exception.WrongEntityException;
import com.softserve.exception.errorinfo.ErrorInfo;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import java.beans.PropertyVetoException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(DeleteAuthorWithBooksException.class)
    public ResponseEntity<ErrorInfo> deleteAuthorWithBooksExceptionHandler(
            HttpServletRequest request,
            DeleteAuthorWithBooksException exception) {
        ErrorInfo errorInfo = new ErrorInfo(HttpStatus.BAD_REQUEST);
        errorInfo.setUrl(request.getRequestURL().toString());
        errorInfo.setErrorMessage(exception.getMessage());
        LOGGER.error(exception.getMessage());
        return ResponseEntity.status(errorInfo.getStatus()).body(errorInfo);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorInfo> entityNotFoundExceptionHandler(
            HttpServletRequest request,
            EntityNotFoundException exception) {
        ErrorInfo errorInfo = new ErrorInfo(HttpStatus.NOT_FOUND);
        errorInfo.setUrl(request.getRequestURL().toString());
        errorInfo.setErrorMessage(exception.getMessage());
        LOGGER.error(exception.getMessage());
        return ResponseEntity.status(errorInfo.getStatus()).body(errorInfo);
    }

    @ExceptionHandler(WrongEntityException.class)
    public ResponseEntity<ErrorInfo> entityNotFoundExceptionHandler(
            HttpServletRequest request,
            WrongEntityException exception) {
        ErrorInfo errorInfo = new ErrorInfo(HttpStatus.BAD_REQUEST);
        errorInfo.setUrl(request.getRequestURL().toString());
        errorInfo.setErrorMessage(exception.getMessage());
        LOGGER.error(exception.getMessage());
        return ResponseEntity.status(errorInfo.getStatus()).body(errorInfo);
    }

    @ExceptionHandler(PropertyVetoException.class)
    public ResponseEntity<ErrorInfo> propertyVetoExceptionHandler(PropertyVetoException exception) {
        ErrorInfo errorInfo = new ErrorInfo(HttpStatus.INTERNAL_SERVER_ERROR);
        errorInfo.setErrorMessage("Some property represents an unacceptable value");
        LOGGER.error(exception.getMessage());
        return ResponseEntity.status(errorInfo.getStatus()).body(errorInfo);
    }

    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<ErrorInfo> noResultExceptionHandler(HttpServletRequest request, NoResultException exception){
        ErrorInfo errorInfo = new ErrorInfo(HttpStatus.NOT_FOUND);
        errorInfo.setUrl(request.getRequestURL().toString());
        errorInfo.setErrorMessage("Not found entity with this id");
        LOGGER.error(exception.getMessage());
        return ResponseEntity.status(errorInfo.getStatus()).body(errorInfo);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorInfo> constraintViolationExceptionHandler(ConstraintViolationException exception){
        ErrorInfo errorInfo = new ErrorInfo(HttpStatus.INTERNAL_SERVER_ERROR);
        errorInfo.setErrorMessage("Cannot add or update entity, because isbn already exists, reference on not exist entity");
        LOGGER.error(exception.getMessage());
        return ResponseEntity.status(errorInfo.getStatus()).body(errorInfo);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorInfo> illegalStateExceptionHandler(HttpServletRequest request, IllegalStateException exception){
        ErrorInfo errorInfo = new ErrorInfo(HttpStatus.INTERNAL_SERVER_ERROR);
        errorInfo.setUrl(request.getRequestURL().toString());
        errorInfo.setErrorMessage("Cannot add or update entity, some fields are wrong");
        LOGGER.error(exception.getMessage());
        return ResponseEntity.status(errorInfo.getStatus()).body(errorInfo);
    }
}