package com.andresouza.dscatalog.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class NewPasswordDTO {

    @NotNull (message = "Campo obrigatório ")
    private String token;

    @NotNull (message = "Campo obrigatório ")
    @Size(min = 8, message = "Deve ter no mínimo 8 caracteres")
    private String password;

    public NewPasswordDTO(){}

    public NewPasswordDTO(String token, String password) {
        this.token = token;
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewPasswordDTO that = (NewPasswordDTO) o;

        return Objects.equals(token, that.token);
    }

    @Override
    public int hashCode() {
        return token != null ? token.hashCode() : 0;
    }
}
