package com.dc.clientes.clientesrest.service;

import com.dc.clientes.clientesrest.model.Cliente;

import java.util.List;

public interface IClienteService {

    public List<Cliente> findAll();

    public Cliente findById(Long id);

    public Cliente save(Cliente cliente);

    public Cliente delete(Long id);

}
