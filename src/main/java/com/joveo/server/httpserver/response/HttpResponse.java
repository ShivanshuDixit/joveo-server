package com.joveo.server.httpserver.response;

import lombok.Data;

@Data
public class HttpResponse {

    private int statusCode;
    private String body;

    public HttpResponse(int statusCode, String body) {
        super();
        this.statusCode = statusCode;
        this.body = body;
    }
}
