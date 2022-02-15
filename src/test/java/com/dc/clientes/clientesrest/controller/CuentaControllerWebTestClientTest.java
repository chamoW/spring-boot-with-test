/*
 * Copyright (c) 2021. Wladimir López derechos reservados
 */

package com.dc.clientes.clientesrest.controller;

import com.dc.clientes.clientesrest.controller.util.response.HeaderResponse;
import com.dc.clientes.clientesrest.controller.util.response.ResponseData;
import com.dc.clientes.clientesrest.dto.TransaccionDto;
import com.dc.clientes.clientesrest.model.Cuenta;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.IOException;
import java.math.BigDecimal;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class CuentaControllerWebTestClientTest {

    @Autowired
    private WebTestClient webTestClient;


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

    @Test
    void getAll() {
    }

    @Test
    void save() {
    }


}
