package com.dc.clientes.clientesrest.dao;


import com.dc.clientes.clientesrest.dto.Examen;

import java.util.List;

public interface ExamenRepository {
    Examen guardar(Examen examen);
    List<Examen> findAll();
}
