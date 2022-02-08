/*
 * Copyright (c) 2021. Wladimir López derechos reservados
 */

package com.dc.clientes.clientesrest.service.impl;

import com.dc.clientes.clientesrest.dao.ICuentaRepository;
import com.dc.clientes.clientesrest.model.Cuenta;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class CuentaServiceTest {


    @Autowired
    private ICuentaRepository cuentaRepository;


    @Test
    void findAll() {


    }

    @Test
    void save() {
    }
}