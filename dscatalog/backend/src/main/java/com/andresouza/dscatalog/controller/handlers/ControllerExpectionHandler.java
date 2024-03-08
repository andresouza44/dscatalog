package com.andresouza.dscatalog.controller.handlers;

import com.andresouza.dscatalog.dto.StanderError;
import com.andresouza.dscatalog.servicies.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.zip.DataFormatException;

@ControllerAdvice
public class ControllerExpectionHandler {

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

    @ExceptionHandler(DataFormatException.class)
    public ResponseEntity<StanderError> dataBaseException(DataIntegrityViolationException e , HttpServletRequest request){

        HttpStatus status = HttpStatus.BAD_REQUEST;
        StanderError err = new StanderError();
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError(e.getMessage());
        err.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }
}
