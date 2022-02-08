/*
 * Copyright (c) 2021. Wladimir López derechos reservados
 */

package com.dc.clientes.clientesrest.dao;

import com.dc.clientes.clientesrest.model.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ICuentaRepository extends JpaRepository<Cuenta, Long> {
    List<Cuenta> findAll();

    Optional<Cuenta> findById(Long id);

    void update(Cuenta cuenta);


    Cuenta findByPersona(String persona);

    @Query("select c from Cuenta c where  c.persona =?1")
    Optional<Cuenta> findByPersonaQuery(String persona);


}
