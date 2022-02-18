/*
 * Copyright (c) 2021. Wladimir López derechos reservados
 */

package com.dc.clientes.clientesrest.controller;

import com.dc.clientes.clientesrest.controller.util.response.HeaderResponse;
import com.dc.clientes.clientesrest.controller.util.response.ResponseData;
import com.dc.clientes.clientesrest.dto.TransaccionDto;
import com.dc.clientes.clientesrest.model.Cuenta;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class CuentaControllerWebTestClientTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @Order(4)
    void testEliminar() {

        webTestClient.delete().uri("/api/cuentas/1")
                .exchange()
                .expectStatus().isNoContent()
                .expectBody().isEmpty();
    }

    @Test
    @Order(3)
    void testEliminarNotFound() {

        webTestClient.delete().uri("/api/cuentas/10")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .consumeWith(response -> {
                    System.out.println("RESPONSE: "+ response);
                })             ;
    }


    @Test
    @Order(2)
    void testEliminarServerError() {

        webTestClient.delete().uri("/api/cuentas/10")
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody()
                .consumeWith(response -> {
                    System.out.println("RESPONSE: "+ response);
                })             ;
    }

    @Test
    @Order(1)
    void testSave() {
        //GIVEN
        Cuenta cuenta = new Cuenta();
        cuenta.setPersona("Wladimir");
        cuenta.setSaldo(new BigDecimal("3000"));

        //WHEN
        webTestClient.post().uri("/api/cuentas/")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(cuenta)
                .exchange()
                //THEN
                .expectStatus().isCreated()
                .expectBody()
                    .consumeWith(response -> {
                        System.out.println("RESPONSE: "+ response);

                        ObjectMapper objectMapper = new ObjectMapper();

                        try {
                           JsonNode body = objectMapper.readTree(response.getResponseBody());
                           Cuenta responseCuenta = objectMapper.convertValue(body.get("data"), Cuenta.class);
                           assertEquals("Wladimir", responseCuenta.getPersona());

                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    })
                    .jsonPath("$.data.persona").isEqualTo("Wladimir");

    }


    @Test
    void testListarJson() {

        webTestClient.get().uri("/api/cuentas/")
                .exchange()
                .expectBody()
                .jsonPath("$.data").isNotEmpty()
                .jsonPath("$.data").isArray()
                .jsonPath("$.data").value(hasSize(2))
                .jsonPath("$.data[0].persona").isEqualTo("Andres")
                .jsonPath("$.data[0].saldo").isEqualTo("1000.0");

    }



    @Test
    void testListarJsonAndPojo() {

        webTestClient.get().uri("/api/cuentas/")
                .exchange()
                .expectBody()
                    .consumeWith(response -> {
                        System.out.println("RESPONSE: " + response);

                        ObjectMapper mapper = new ObjectMapper();

                        try {
                            JsonNode json = mapper.readTree(response.getResponseBody());

                            List<Cuenta> list = mapper.convertValue(json.get("data"), new TypeReference<List<Cuenta>>() {});

                            //Cuenta cuenta = objectMapper.convertValue(jsonNode.get("data"), Cuenta.class);

                            assertEquals(2, list.size());



                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

    }


    @Test
    void testDetalle() {

        webTestClient.get().uri("/api/cuentas/1")
                .exchange()//para que se ejecuta el servicio y enviar datos
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                    .consumeWith(result -> {
                        System.out.println("RESPONSE: " + result);
                    })
                    .jsonPath("$.data.persona").isEqualTo("Andres");
    }

    @Test
    void testDetallePojo() {

        webTestClient.get().uri("/api/cuentas/2")
                .exchange()//para que se ejecuta el servicio y enviar datos
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ResponseData.class)
                .consumeWith(response -> {
                    System.out.println("RESPONSE: " + response);


                    ResponseData responseData = response.getResponseBody();

                    HeaderResponse header =  responseData.getHeader();

                    assertEquals("200", header.getCode());
                })
                ;
    }


    @Test
    void testDetalleJsonAndPojo() {

        webTestClient.get().uri("/api/cuentas/2")
                .exchange()//para que se ejecuta el servicio y enviar datos
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(response -> {
                    System.out.println("RESPONSE: " + response);

                    ObjectMapper objectMapper = new ObjectMapper();

                    try {
                        JsonNode jsonNode = objectMapper.readTree(response.getResponseBody());

                        //List<Book> bookList = objectMapper.convertValue(jsonNode, new TypeReference<List<Book>>() {});

                        Cuenta cuenta = objectMapper.convertValue(jsonNode.get("data"), Cuenta.class);

                        assertEquals("John", cuenta.getPersona());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }




                })
        ;
    }
/*


*/
    @Test
    void testTransferir() {
        // GIVEN
        TransaccionDto dto = new TransaccionDto();
        dto.setCuentaOrigenId(1L);
        dto.setCuentaDestinoId(2L);
        dto.setBancoId(1L);
        dto.setMonto(new BigDecimal("100"));

        //WHEN
        webTestClient.post().uri("/api/cuentas/transferMoney")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dto)
                .exchange()// Para enviar datos

                //THEN
                .expectStatus().isOk()
                .expectBody()
                    .consumeWith(result -> {

                        ObjectMapper mapper = new ObjectMapper();

                        try {
                            System.out.println("RESPONSE: " + result);
                            JsonNode json = mapper.readTree(result.getResponseBody());
                            System.out.println("RESPONSE: " + json.toPrettyString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    })
                    .jsonPath("$.header.code").value(is("200"))
                    .jsonPath("$.header.message").value(value ->
                        assertEquals("Transferencia realizada con éxito!", value))
                    .jsonPath("$.data").isEmpty()
        ;


    }




}
