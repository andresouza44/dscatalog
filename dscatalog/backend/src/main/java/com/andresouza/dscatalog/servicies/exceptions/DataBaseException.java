package com.andresouza.dscatalog.servicies.exceptions;

import org.springframework.dao.DataIntegrityViolationException;

public class DataBaseException extends DataIntegrityViolationException {

    public DataBaseException (String message){
        super(message);
    }
}
