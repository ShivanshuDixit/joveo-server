package com.joveo.server.entity.error;

import java.io.Serializable;

import lombok.Data;

@Data
public class Error implements Serializable {

    private static final long serialVersionUID = 1L;
    private String error;

    public Error(String error) {
        this.error = error;
    }
}
