/*
 * Copyright (c) 2021. Wladimir López derechos reservados
 */

package com.dc.clientes.clientesrest.controller;

import com.dc.clientes.clientesrest.controller.util.response.HeaderResponse;
import com.dc.clientes.clientesrest.controller.util.response.ResponseData;
import com.dc.clientes.clientesrest.dto.TransaccionDto;
import com.dc.clientes.clientesrest.model.Cuenta;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CuentaControllerRestTemplateTest {

    @Autowired
    private TestRestTemplate client;

    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }








    @Test
    @Order(1)
    void testSave() {

        Cuenta cuenta = new Cuenta();
        cuenta.setPersona("Pepa");
        cuenta.setSaldo(new BigDecimal("3000"));

        ResponseEntity<ResponseData>  response =  client.postForEntity("/api/cuentas/", cuenta, ResponseData.class);
        HeaderResponse header = response.getBody().getHeader();

        assertAll(() -> {
            assertNotNull(header);
            assertEquals("Registro guardado correctamente", header.getMessage());
            assertEquals("200", header.getCode());

        });

    }


    @Test
    @Order(2)
    void testEliminar() {
        client.delete("/api/cuentas/3");
    }

    @Test
    void testDetalle() {
        ResponseEntity<ResponseData> response = client.getForEntity("/api/cuentas/1", ResponseData.class);

        ResponseData responseData= response.getBody();

        Cuenta cuenta = mapper.convertValue(responseData.getData(), Cuenta.class);

        System.out.println("CUENTA: "+ cuenta);


        assertAll( () -> {
            assertNotNull(cuenta);
            assertEquals(HttpStatus.OK,response.getStatusCode() );
            assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
            assertEquals("Andres", cuenta.getPersona());
            assertEquals("800.0", cuenta.getSaldo().toPlainString());
        });

    }



    @Test
    void testGetAll() {
        ResponseEntity<ResponseData> response = client.getForEntity("/api/cuentas/", ResponseData.class);

        ResponseData responseData= response.getBody();

        List<Cuenta> cuentas = mapper.convertValue(responseData.getData(), new TypeReference<List<Cuenta>>() {});
        assertAll(() -> {
            assertNotNull(cuentas);
            assertFalse(cuentas.isEmpty());
            assertEquals("Andres", cuentas.get(0).getPersona());

            //Leer como json
            JsonNode json = mapper.readTree(mapper.writeValueAsString(cuentas));
            assertEquals( "\"Andres\"", json.get(0).path("persona"));

        });



    }


    @Test
    @Order(3)
    void testTransferMoney() throws JsonProcessingException {
        TransaccionDto dto = new TransaccionDto();
        dto.setMonto(new BigDecimal("100"));
        dto.setCuentaDestinoId(2L);
        dto.setCuentaOrigenId(1L);
        dto.setBancoId(1L);

        ResponseData response =  client.postForObject("/api/cuentas/transferMoney", dto, ResponseData.class);

        assertEquals("Transferencia realizada con éxito!", response.getHeader().getMessage());

        ResponseEntity<String> responseJson =  client.postForEntity("/api/cuentas/transferMoney", dto, String.class);

        String json = responseJson.getBody();
        System.out.println("RESPONSE JSON: " +json);

        assertNotNull(json);
        assertEquals(HttpStatus.OK, responseJson.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseJson.getHeaders().getContentType());
        assertTrue(json.contains("Transferencia realizada con éxito!"));


        JsonNode jsonNode = mapper.readTree(json);
        assertEquals("Transferencia realizada con éxito!", jsonNode.path("header").path("message").asText());

    }

}