package httpserver.handlers;


import java.util.logging.Logger;

import entity.job.JobRequestHandler;
import httpserver.request.HttpRequest;
import httpserver.response.HttpResponse;
import httpserver.response.ResponseGenerator;

public class RequestHandler {

    private ResponseGenerator responseGenerator;
    private JobRequestHandler jobRequestHandler;
    private Logger log = Logger.getLogger(RequestHandler.class.getName());


    public RequestHandler(ResponseGenerator responseGenerator) {
        this.responseGenerator = responseGenerator;
        this.jobRequestHandler = new JobRequestHandler();
    }


    public String handle(HttpRequest request) throws Exception {

        HttpResponse httpResponse = jobRequestHandler.handleRequest(request);
        String responseBody = responseGenerator.getResponseString(httpResponse.getStatusCode(), httpResponse.getBody());
        log.info("\n\nSending Response:");
        log.info(responseBody);

        return responseBody;
    }

}
