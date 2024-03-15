package com.andresouza.dscatalog.controller.exceptions;

public class FieldMessage {
    private String fildName;
    private String message;

    private FieldMessage (){
    }

    public FieldMessage(String fildName, String message) {
        this.fildName = fildName;
        this.message = message;
    }

    public String getFildName() {
        return fildName;
    }

    public String getMessage() {
        return message;
    }
}
