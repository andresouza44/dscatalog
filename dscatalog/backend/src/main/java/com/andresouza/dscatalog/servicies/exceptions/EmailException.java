package com.andresouza.dscatalog.servicies.exceptions;

import org.springframework.mail.MailException;

public class EmailException extends MailException {

    public EmailException (String msg){
        super(msg);
    }
}
