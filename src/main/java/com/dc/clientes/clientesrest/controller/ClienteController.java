package com.dc.clientes.clientesrest.controller;

import com.dc.clientes.clientesrest.controller.util.response.ResponseData;
import com.dc.clientes.clientesrest.controller.util.response.ResponseFactory;
import com.dc.clientes.clientesrest.model.Cliente;
import com.dc.clientes.clientesrest.service.impl.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(CommonController.API_CLIENTES)
public class ClienteController extends CommonController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/")
    public ResponseData getAll() {
        try {

            List<Cliente> data = clienteService.findAll();

            return ResponseFactory.getSuscessResponse(data);

        } catch (Exception e) {
            return ResponseFactory.getErrorResponse(e.getMessage());

        }


    }

    @GetMapping("/{id}")
    public ResponseData getById(@PathVariable Long id) {
        try {
            Cliente data = clienteService.findById(id);
            return ResponseFactory.getSuscessResponse(data);

        } catch (Exception e) {
            return ResponseFactory.getErrorResponse(e.getMessage());
        }

    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseData create(@RequestBody Cliente cliente) {
        try {
            Cliente data = clienteService.save(cliente);
            return ResponseFactory.getSuscessResponse(data, "Cliente creado correctamente");
        } catch (Exception e) {
            return ResponseFactory.getErrorResponse(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseData update(@RequestBody Cliente cliente, @PathVariable Long id) {
        try {
            Cliente data = clienteService.findById(id, cliente);
            return ResponseFactory.getSuscessResponse(data, "Cliente actualizado correctamente");


        } catch (Exception e) {
            return ResponseFactory.getErrorResponse(e.getMessage());
        }

    }

    @DeleteMapping("/{id}")
    public ResponseData delete(@PathVariable Long id) {

        try {
            Cliente data = clienteService.delete(id);

            return ResponseFactory.getSuscessResponse(data, "Cliente eliminado correctamente");

        } catch (Exception e) {
            return ResponseFactory.getErrorResponse(e.getMessage());
        }
    }


}
