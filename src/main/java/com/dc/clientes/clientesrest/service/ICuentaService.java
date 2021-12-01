/*
 * Copyright (c) 2021. Wladimir López derechos reservados
 */

package com.dc.clientes.clientesrest.service;

import com.dc.clientes.clientesrest.model.Cuenta;

import java.math.BigDecimal;

public interface ICuentaService {

    Cuenta findById(Long id);

     int revisarTotalTranferencias(Long bancoId);

     BigDecimal revisarSaldo(Long cuentaId);

     void transferir(Long numCuentaOrigen, Long numCuentaDestino, BigDecimal mongo, Long bancoId);
}
