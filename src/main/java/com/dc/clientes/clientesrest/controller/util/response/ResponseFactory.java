package com.dc.clientes.clientesrest.controller.util.response;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ResponseFactory {

    public static ResponseData getSuscessResponse(Object data, String message) {
        if (data == null) {
            return new ResponseData(getEmptyDataHeader());
        }

        return new ResponseData(getSuccessHeader(message), data);
    }

    public static ResponseData getSuscessResponse(Object data) {

        if (data == null) {
            return new ResponseData(getEmptyDataHeader());
        }

        if (data.getClass().isArray()) {
            return new ResponseData(getSuccessHeader("Datos cargados correctamente"), data);
        } else if (data instanceof String) {
            return new ResponseData(getSuccessHeader(data.toString()));

        } else {
            return new ResponseData(getSuccessHeader("Registro encontrado"), data);
        }
    }


    public static ResponseData getErrorResponse(String message) {
        return new ResponseData(getErrorHeader(message));
    }


    private static final HeaderResponse getSuccessHeader(String message) {
        HeaderResponse header = getGeneralHeader(message);

        return header;
    }

    private static final HeaderResponse getEmptyDataHeader() {
        HeaderResponse header = getGeneralHeader("No existe el registro");
        header.setCode("ERR_001");

        return header;
    }


    private static final HeaderResponse getErrorHeader(String message) {
        HeaderResponse header = getGeneralHeader(message);
        header.setCode(HttpStatus.BAD_REQUEST.value() + "");

        return header;
    }

    private static final HeaderResponse getGeneralHeader(String message) {

        HeaderResponse header = new HeaderResponse();
        header.setCode(HttpStatus.OK.value() + "");
        header.setMessage(message);
        header.setTimestap(LocalDateTime.now());

        return header;

    }


}
