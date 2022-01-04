package com.dc.clientes.clientesrest.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"http://localhost:4200"})
public class CommonController {


    public static final String API_URL = "/api";
    public  static final String API_CLIENTES = API_URL + "/clientes";
}
