/*
 * Copyright (c) 2021. Wladimir López derechos reservados
 */

package com.dc.clientes.clientesrest.service.impl;

import com.dc.clientes.clientesrest.dao.IBancoRepository;
import com.dc.clientes.clientesrest.dao.ICuentaRepository;
import com.dc.clientes.clientesrest.model.Banco;
import com.dc.clientes.clientesrest.model.Cuenta;
import com.dc.clientes.clientesrest.service.ICuentaService;
import com.sun.javaws.exceptions.InvalidArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CuentaService implements ICuentaService {

    @Autowired
    private ICuentaRepository cuentaRepository;

    @Autowired
    private IBancoRepository bancoRepository;

    @Override
    public Cuenta findById(Long id) {
        return cuentaRepository.findById(id).orElseThrow(() -> new NullPointerException());
    }

    @Override
    public int revisarTotalTranferencias(Long bancoId) {
        Banco banco = bancoRepository.findById(bancoId).orElseThrow(() -> new NullPointerException("Error"));
        return banco.getTotalTransferencias();
    }

    @Override
    public BigDecimal revisarSaldo(Long cuentaId) {
        Cuenta cuenta = cuentaRepository.findById(cuentaId).orElseThrow(() -> new NullPointerException("Error"));
        return cuenta.getSaldo();
    }

    @Override
    public void transferir(Long numCuentaOrigen, Long numCuentaDestino, BigDecimal monto, Long bancoId) {
        Cuenta cuentaOrigen = cuentaRepository.findById(numCuentaOrigen).orElseThrow(() -> new NullPointerException("Error"));
        cuentaOrigen.debito(monto);
        cuentaRepository.save(cuentaOrigen);

        Cuenta cuentaDestino = cuentaRepository.findById(numCuentaDestino).orElseThrow(() -> new NullPointerException("Error"));
        cuentaDestino.credito(monto);
        cuentaRepository.save(cuentaDestino);
    }
}
