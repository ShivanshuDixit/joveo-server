package com.joveo.server.entity.job;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.UUID;

import com.joveo.server.entity.error.Error;
import com.joveo.server.httpserver.request.HttpRequest;
import com.joveo.server.httpserver.response.HttpResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JobRequestHandler {

    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Handles put , post , delete and get requests
      * @param request request
     * @return response
     * @throws Exception exception
     */
    public HttpResponse handleRequest(HttpRequest request) throws Exception {

        HttpResponse httpResponse = new HttpResponse(400, objectMapper.writeValueAsString
                (new Error("Invalid endpoint")));
        try {
            switch (request.getRequestMethod()) {
                case PUT:
                    if ("/job".equals(request.getUrl())) {
                        httpResponse = handleCreateJob(request.getBody());
                    }
                    break;

                case GET:
                    if (request.getUrl().startsWith("/job")) {
                        httpResponse = handleGetJob(request.getQueryParams().get("id"));
                    }
                    break;
                case DELETE:
                    if (request.getUrl().startsWith("/job")) {
                        httpResponse = handleDeleteJob(request.getQueryParams().get("id"));
                    }
                    break;
                case POST:
                    if (request.getUrl().startsWith("/job")) {
                        httpResponse = handleUpdateJob(request.getBody());
                    }
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            log.error("Exception occurred in handleRequest ", e);
        }

        return httpResponse;
    }

    /**
     * Handles post requests (update job)
     * @param body
     * @return response
     * @throws Exception exception
     */
    private HttpResponse handleUpdateJob(String body) throws Exception {
        HttpResponse httpResponse;

        log.info("Querying for body: " + body);
        if (body == null || body.isEmpty()) {
            httpResponse = new HttpResponse(400, objectMapper.writeValueAsString
                    (new Error("Body cannot be empty in this request")));
            return httpResponse;
        }
        Job inputJob = objectMapper.readValue(body, Job.class);
        String id = inputJob.getId();
        Job job = JobStore.jobStoreMap.get(id);
        if (id == null || id.isEmpty() || job == null) {
            httpResponse = new HttpResponse(404, objectMapper.writeValueAsString(new Error("Unknown Job Id")));
            log.error("Querying for body: " + body);
            return httpResponse;
        }
        job.setName(inputJob.getName());
        JobStore.jobStoreMap.put(id, job);
        return new HttpResponse(200, "{\"response\":true}");
    }

    /**
     * Handles delete request
     * @param id
     * @return response
     * @throws Exception
     */
    private HttpResponse handleDeleteJob(String id) throws Exception {

        HttpResponse httpResponse;
        log.info("Querying for ID: " + id);
        if (id == null || id.isEmpty()) {
            httpResponse = new HttpResponse(400, objectMapper.writeValueAsString
                    (new Error("Id cannot be empty in this request")));
            return httpResponse;
        }
        Job job = JobStore.jobStoreMap.get(id);
        if (job == null || job.getName() == null) {
            httpResponse = new HttpResponse(404, objectMapper.writeValueAsString(new Error("Unknown Job Id")));
            return httpResponse;
        }
        JobStore.jobStoreMap.remove(id);
        return new HttpResponse(200, "{\"response\":true}");
    }

    /**
     * Hanldes get request
     * @param id id
     * @return response
     * @throws Exception exception
     */
    private HttpResponse handleGetJob(String id) throws Exception {

        HttpResponse httpResponse;
        log.info("Querying for ID: " + id);
        if (id == null || id.isEmpty()) {
            httpResponse = new HttpResponse(400, objectMapper.writeValueAsString
                    (new Error("Id cannot be empty in this request")));
            return httpResponse;
        }
        Job job = JobStore.jobStoreMap.get(id);
        if (job == null || job.getName() == null) {
            httpResponse = new HttpResponse(404, objectMapper.writeValueAsString(new Error("Unknown Job Id")));
            return httpResponse;
        }
        CreateJobRequest createJobRequest = new CreateJobRequest();
        createJobRequest.setName(job.getName());
        return new HttpResponse(200, objectMapper.writeValueAsString(createJobRequest));
    }

    /**
     * Handles Put request
     * @param body body
     * @return response
     * @throws Exception exception
     */
    private HttpResponse handleCreateJob(String body) throws Exception {

        HttpResponse httpResponse;
        CreateJobRequest createJobRequest = objectMapper.readValue(body, CreateJobRequest.class);
        log.info("Running for" + createJobRequest.getName());
        if (createJobRequest.getName() == null || createJobRequest.getName().isEmpty()) {
            httpResponse = new HttpResponse(400, objectMapper.writeValueAsString
                    (new Error("Name cannot be empty in this request")));
            return httpResponse;
        }
        String uuid = UUID.randomUUID().toString();
        Job job = new Job();
        job.setName(createJobRequest.getName());
        job.setId(uuid);
        JobStore.jobStoreMap.put(uuid, job);
        log.info("Data till now:" + JobStore.jobStoreMap);
        CreateJobResponse createJobResponse = new CreateJobResponse();
        createJobResponse.setId(uuid);
        return new HttpResponse(200, objectMapper.writeValueAsString(createJobResponse));
    }
}
