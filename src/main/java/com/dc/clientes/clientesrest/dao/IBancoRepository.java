/*
 * Copyright (c) 2021. Wladimir López derechos reservados
 */

package com.dc.clientes.clientesrest.dao;

import com.dc.clientes.clientesrest.model.Banco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IBancoRepository  extends JpaRepository<Banco, Long> {
    List<Banco> findAll();

    Optional<Banco> findById(Long id);

    Banco save(Banco banco);
}
