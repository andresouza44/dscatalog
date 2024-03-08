package com.andresouza.dscatalog.dto;

import jakarta.persistence.Column;

import java.time.Instant;

public class StanderError {
    private Instant timestamp;
    private Integer status;
    private String error;
    private  String path;

    public StanderError(){
    }

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIMEZONE")
    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}

