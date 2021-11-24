package com.dc.clientes.clientesrest.service.impl;


import com.dc.clientes.clientesrest.dao.IClienteDao;
import com.dc.clientes.clientesrest.model.Cliente;
import com.dc.clientes.clientesrest.service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteService implements IClienteService {

    @Autowired
    private IClienteDao clienteDao;

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> findAll() {
        return clienteDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente findById(Long id) {
        return clienteDao.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public Cliente findById(Long id, Cliente cliente) {
        Cliente clienteActual = clienteDao.findById(id).orElse(null);

        if (clienteActual != null) {
            clienteActual.setNombre(cliente.getNombre());
            clienteActual.setApellido(cliente.getApellido());
            clienteActual.setEmail(cliente.getEmail());

            return save(clienteActual);
        }
        return null;

    }

    @Override
    @Transactional
    public Cliente save(Cliente cliente) {
        return clienteDao.save(cliente);
    }

    @Override
    @Transactional
    public Cliente delete(Long id) {

        Cliente clienteActual = clienteDao.findById(id).orElse(null);
        if (clienteActual != null) {
            clienteDao.deleteById(id);
            return clienteActual;
        }

        return clienteActual;
    }
}