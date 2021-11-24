package com.dc.clientes.clientesrest.controller.util.response;

public class ResponseData {

    private HeaderResponse header;
    private Object data;

    public ResponseData() {

    }

    public ResponseData(HeaderResponse header, Object data) {
        this.header = header;
        this.data = data;
    }


    public ResponseData(HeaderResponse header) {
        this.header = header;
    }

    public HeaderResponse getHeader() {
        return header;
    }

    public void setHeader(HeaderResponse header) {
        this.header = header;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
