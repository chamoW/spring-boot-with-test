/*
 * Copyright (c) 2021. Wladimir López derechos reservados
 */

package com.dc.clientes.clientesrest.controller;

import com.dc.clientes.clientesrest.controller.util.response.ResponseData;
import com.dc.clientes.clientesrest.dto.TransaccionDto;
import com.dc.clientes.clientesrest.model.Cliente;
import com.dc.clientes.clientesrest.model.Cuenta;
import com.dc.clientes.clientesrest.service.impl.CuentaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dc.clientes.clientesrest.controller.util.response.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(CommonController.API_CUENTAS)
public class CuentaController extends CommonController {

    @Autowired
    private CuentaService cuentaService;

    @GetMapping
    public ResponseData getAll() {
        try {

            List<Cuenta> data = cuentaService.findAll();

            return ResponseFactory.getSuscessResponse(data);

        } catch (Exception e) {
            return ResponseFactory.getErrorResponse(e.getMessage());

        }


    }

    @PostMapping
    public ResponseData save(@RequestBody Cuenta cuenta){
        try {

            Cuenta data = cuentaService.save(cuenta);

            return ResponseFactory.getSuscessResponse(data, "Registro guardado correctamente");

        } catch (Exception e) {
            return ResponseFactory.getErrorResponse(e.getMessage());

        }
    }


    @GetMapping("/{id}")
    public ResponseData detalle(@PathVariable Long id) {
        try {
            Cuenta cuenta = cuentaService.findById(id);
            return ResponseFactory.getSuscessResponse(cuenta);
        } catch (Exception e) {
            return ResponseFactory.getErrorResponse(e.getMessage());
        }
    }

    @PostMapping("/transferMoney")
    public ResponseData transferMoney(@RequestBody TransaccionDto dto) {
        try {

            cuentaService.transferir(dto.getCuentaOrigenId(),
                    dto.getCuentaDestinoId(),
                    dto.getMonto(), dto.getBancoId());

            return ResponseFactory.getSuscessResponse("Transferencia realizada con éxito!");

        } catch (Exception e) {
            return ResponseFactory.getErrorResponse(e.getMessage());
        }


    }


    @PostMapping("/transferir1")
    public ResponseEntity<?> transferir(@RequestBody TransaccionDto dto) {
        cuentaService.transferir(dto.getCuentaOrigenId(),
                dto.getCuentaDestinoId(),
                dto.getMonto(), dto.getBancoId());

        Map<String, Object> response = new HashMap<>();
        response.put("date", LocalDate.now().toString());
        response.put("status", "OK");
        response.put("mensaje", "Transferencia realizada con éxito!");
        response.put("transaccion", dto);

        return ResponseEntity.ok(response);
    }
}
