package com.dc.clientes.clientesrest.exceptions;/*
 * Copyright (c) 2021. Wladimir López derechos reservados
 */


public class DineroInsuficienteException extends RuntimeException{
    public DineroInsuficienteException(String message) {
        super(message);
    }
}
