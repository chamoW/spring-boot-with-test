/*
 * Copyright (c) 2021. Wladimir López derechos reservados
 */

package com.dc.clientes.clientesrest.dao;

import com.dc.clientes.clientesrest.model.Banco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBancoRepository  extends JpaRepository<Banco, Long> {

}
