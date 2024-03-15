package com.andresouza.dscatalog.controller.exceptions;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StanderError {

        private List<FieldMessage> errors = new ArrayList<>();

        public List<FieldMessage> getErrors() {
            return errors;
        }

        public void addError (String fieldName, String message){
            errors.add(new FieldMessage(fieldName, message));
        }
    }




