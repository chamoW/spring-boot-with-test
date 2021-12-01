/*
 * Copyright (c) 2021. Wladimir López derechos reservados
 */

package com.dc.clientes.clientesrest;

import com.dc.clientes.clientesrest.dao.ICuentaRepository;
import com.dc.clientes.clientesrest.model.Cuenta;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class IntegractionJpaTest {

    @Autowired
    private ICuentaRepository cuentaRepository;


    @Test
    void testFindById() {
        Optional<Cuenta> cuenta = cuentaRepository.findById(1L);

        assertAll(() -> {
            assertTrue(cuenta.isPresent());
            assertEquals("Andres", cuenta.orElseThrow(() -> new IllegalArgumentException()).getPersona());
        });
    }


    @Test
    void testFindByPersona() {
        Optional<Cuenta> cuenta = cuentaRepository.findByPersonaQuery("Andres");

        assertAll(() -> {
            assertTrue(cuenta.isPresent());
            assertEquals("Andres", cuenta.orElseThrow(() -> new IllegalArgumentException()).getPersona());
            assertEquals("1000.00", cuenta.orElseThrow(() -> new IllegalArgumentException()).getSaldo().toPlainString());
        });
    }

    @Test
    void testFindByPersonaExeption() {
        Optional<Cuenta> cuenta = cuentaRepository.findByPersonaQuery("RobKin");

        assertAll(() -> {
            assertFalse(cuenta.isPresent());
            assertThrows(IllegalArgumentException.class, () -> {
                cuenta.orElseThrow(() -> new IllegalArgumentException());
            });
        });
    }


    @Test
    void testFindAll() {
        List<Cuenta> cuentas = cuentaRepository.findAll();
        assertAll(() -> {
            assertFalse(cuentas.isEmpty());
            assertEquals(2, cuentas.size());
        });

    }


    @Test
    void testSave() {
        Cuenta cuenta = new Cuenta();
        cuenta.setPersona("Wladimir");
        cuenta.setSaldo(new BigDecimal("3000"));

        cuentaRepository.save(cuenta);


        Cuenta cuentaWladimir =    cuentaRepository.findByPersona("Wladimir");

        assertEquals("Wladimir", cuentaWladimir.getPersona());
        assertEquals("3000", cuentaWladimir.getSaldo().toPlainString());

    }

    @Test
    void testUpdate() {
        Cuenta cuenta = new Cuenta();
        cuenta.setPersona("Wladimir");
        cuenta.setSaldo(new BigDecimal("3000"));

        Cuenta cuentaWladimir =   cuentaRepository.save(cuenta);

        assertEquals("Wladimir", cuentaWladimir.getPersona());
        assertEquals("3000", cuentaWladimir.getSaldo().toPlainString());

        cuentaWladimir.setSaldo(new BigDecimal("3500"));

        Cuenta cuentaActualizada = cuentaRepository.save(cuentaWladimir);
        assertEquals("3500", cuentaActualizada.getSaldo().toPlainString());
    }

    @Test
    void testDelete() {
        Cuenta cuenta = cuentaRepository.findById(2L).orElseThrow(() -> new IllegalArgumentException());
        assertEquals("John", cuenta.getPersona());

        cuentaRepository.delete(cuenta);


        assertThrows(IllegalArgumentException.class, () -> {
            cuentaRepository.findById(2L).orElseThrow(() -> new IllegalArgumentException());
        });


    }


}
