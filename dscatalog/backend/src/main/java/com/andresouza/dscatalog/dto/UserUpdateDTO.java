package com.andresouza.dscatalog.dto;


import com.andresouza.dscatalog.entities.validation.UserUpdateValid;

@UserUpdateValid
public class UserUpdateDTO extends UserDto{
    private String password;

    public UserUpdateDTO(){

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
