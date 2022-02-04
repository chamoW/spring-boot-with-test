/*
 * Copyright (c) 2021. Wladimir López derechos reservados
 */

package com.dc.clientes.clientesrest.service;

import com.dc.clientes.clientesrest.model.Cliente;
import com.dc.clientes.clientesrest.model.Cuenta;

import java.math.BigDecimal;
import java.util.List;


public interface ICuentaService {

    public List<Cuenta> findAll();

    Cuenta findById(Long id);

     int revisarTotalTranferencias(Long bancoId);

     BigDecimal revisarSaldo(Long cuentaId);

     void transferir(Long numCuentaOrigen, Long numCuentaDestino, BigDecimal mongo, Long bancoId);
}
