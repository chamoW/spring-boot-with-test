package com.dc.clientes.clientesrest.dao;

import com.dc.clientes.clientesrest.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IClienteDao extends JpaRepository<Cliente, Long> {



}
