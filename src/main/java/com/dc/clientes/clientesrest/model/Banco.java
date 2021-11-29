
/*
 * Copyright (c) 2021. Wladimir López derechos reservados
 */

package com.dc.clientes.clientesrest.model;

import javax.persistence.*;

@Entity
@Table(name = "bancos")
public class Banco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(name = "total_tranfers")
    private int totalTransferencias;

    public Banco() {
    }

    public Banco(Long id, String persona, int saldo) {
        this.id = id;
        this.nombre = persona;
        this.totalTransferencias = saldo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String persona) {
        this.nombre = persona;
    }

    public int getTotalTransferencias() {
        return totalTransferencias;
    }

    public void setTotalTransferencias(int saldo) {
        this.totalTransferencias = saldo;
    }
}
