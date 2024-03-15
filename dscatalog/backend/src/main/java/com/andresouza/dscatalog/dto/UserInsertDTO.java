package com.andresouza.dscatalog.dto;

import com.andresouza.dscatalog.entities.validation.UserInsertValid;

@UserInsertValid
public class UserInsertDTO  extends UserDto{
    private String password;

    public UserInsertDTO(){

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
