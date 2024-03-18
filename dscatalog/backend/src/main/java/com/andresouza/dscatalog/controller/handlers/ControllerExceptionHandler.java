package com.andresouza.dscatalog.controller.handlers;

import com.andresouza.dscatalog.controller.exceptions.StanderError;
import com.andresouza.dscatalog.controller.exceptions.ValidationError;
import com.andresouza.dscatalog.servicies.exceptions.DataBaseException;
import com.andresouza.dscatalog.servicies.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.zip.DataFormatException;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StanderError> resourceNotFound(RuntimeException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;

        StanderError err = new StanderError();
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError(e.getMessage());
        err.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(err);

    }

    @ExceptionHandler(DataBaseException.class)
    public ResponseEntity<StanderError> dataBaseException(DataIntegrityViolationException e , HttpServletRequest request){

        HttpStatus status = HttpStatus.BAD_REQUEST;
        StanderError err = new StanderError();
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError(e.getMessage());
        err.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> methodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        ValidationError err = new ValidationError();

        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError(e.getMessage());
        err.setPath(request.getRequestURI());

        e.getBindingResult().getFieldErrors().forEach(field ->
                err.addError(field.getField(), field.getDefaultMessage()));

                return ResponseEntity.status(status).body(err);
    }



    }

