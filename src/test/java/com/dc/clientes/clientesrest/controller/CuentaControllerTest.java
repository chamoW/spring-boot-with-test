/*
 * Copyright (c) 2021. Wladimir López derechos reservados
 */

package com.dc.clientes.clientesrest.controller;

import com.dc.clientes.clientesrest.dto.TransaccionDto;
import com.dc.clientes.clientesrest.model.Cuenta;
import com.dc.clientes.clientesrest.service.impl.CuentaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(CuentaController.class)
class CuentaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CuentaService service;

    @Test
    void testTransferMoney() throws Exception {
        //Given
        TransaccionDto dto = new TransaccionDto();
        dto.setCuentaOrigenId(1L);
        dto.setCuentaDestinoId(2L);
        dto.setMonto(new BigDecimal("100"));
        dto.setBancoId(1L);

        ObjectMapper objectMapper = new ObjectMapper();


        //When
        mockMvc.perform(
                post("/api/cuentas/transferMoney")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))

                //Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.header.code").value("200"))
                .andExpect(jsonPath("$.header.message").value("Transferencia realizada con éxito!"))
                .andDo(print());


    }

    @Test
    void testListar() throws Exception {
        //GIVEN
        Cuenta cuenta1 = new Cuenta();
        cuenta1.setId(1l);
        cuenta1.setPersona("Wladimir");

        List<Cuenta> cuentas = new ArrayList<>();
        cuentas.add(cuenta1);

        when(service.findAll()).thenReturn(cuentas);

        //WHEN
        mockMvc.perform(get("/api/cuentas/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data[0].id").value(1l))
                .andExpect(jsonPath("$.data[0].persona").value("Wladimir"))
                .andExpect(jsonPath("$.data", hasSize(1)))

        ;

        verify(service).findAll();
    }

    @Test
    void testSave() throws Exception {
        //Given
        Cuenta cuenta = new Cuenta(null, "Wladimir", new BigDecimal("3000"));

        when(service.save(any())).then(invocation -> {
            //Se captura lo enviado y se cambia la data
            Cuenta cuentaModificated = invocation.getArgument(0);
            cuentaModificated.setPersona("Wladimir López");
            cuentaModificated.setId(3L);
            return cuentaModificated;
        });

        ObjectMapper objectMapper = new ObjectMapper();

        //When
        mockMvc.perform(post("/api/cuentas/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cuenta))
        )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.header.code").value("200"))
                .andExpect(jsonPath("$.data.id", is(3)))
                .andExpect(jsonPath("$.data.persona", is("Wladimir López")))
                .andExpect(jsonPath("$.data.saldo", is(3000)))
                .andDo(print());


        //Para verificar que se pasa por el médot save del servicio
        verify(service).save(any());

    }
}