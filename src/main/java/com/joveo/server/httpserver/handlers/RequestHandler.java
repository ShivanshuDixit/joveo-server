package com.joveo.server.httpserver.handlers;


import com.joveo.server.entity.job.JobRequestHandler;
import com.joveo.server.httpserver.request.HttpRequest;
import com.joveo.server.httpserver.response.HttpResponse;
import com.joveo.server.httpserver.response.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RequestHandler {

    private ResponseGenerator responseGenerator;
    private JobRequestHandler jobRequestHandler;


    public RequestHandler(ResponseGenerator responseGenerator) {
        this.responseGenerator = responseGenerator;
        this.jobRequestHandler = new JobRequestHandler();
    }

    /**
     * Handles request and pass its on to the dedicated handler
     * @param request request
     * @return response body
     * @throws Exception exception
     */
    public String handle(HttpRequest request) throws Exception {

        HttpResponse httpResponse = jobRequestHandler.handleRequest(request);
        String responseBody = responseGenerator.getResponseString(httpResponse.getStatusCode(), httpResponse.getBody());
        log.info("\n\nSending Response:");
        log.info(responseBody);

        return responseBody;
    }

}
