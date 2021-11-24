package com.dc.clientes.clientesrest.controller.util.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class HeaderResponse {

    private String message;
    private String code;
    private LocalDateTime timestap;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getTimestap() {
        return timestap;
    }

    public void setTimestap(LocalDateTime timestap) {
        this.timestap = timestap;
    }
}
