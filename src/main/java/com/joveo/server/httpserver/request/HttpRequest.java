package com.joveo.server.httpserver.request;

import java.util.Map;

import lombok.Data;

@Data
public class HttpRequest {

    private String url;

    private RequestMethod requestMethod;

    private String body;

    private Map<String, String> headers;

    private Map<String, String> queryParams;


    public HttpRequest(String url, RequestMethod requestMethod, String body,
                       Map<String, String> headers, Map<String, String> queryParams) {
        super();
        this.url = url;
        this.requestMethod = requestMethod;
        this.body = body;
        this.headers = headers;
        this.queryParams = queryParams;
    }
}

