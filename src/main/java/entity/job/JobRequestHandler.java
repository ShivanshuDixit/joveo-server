package entity.job;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.UUID;
import java.util.logging.Logger;

import entity.error.ErrorModel;
import httpserver.request.HttpRequest;
import httpserver.response.HttpResponse;

public class JobRequestHandler {

    private Logger log = Logger.getLogger(JobRequestHandler.class.getName());

    private ObjectMapper objectMapper = new ObjectMapper();

    public HttpResponse handleRequest(HttpRequest request) throws Exception {

        HttpResponse httpResponse = new HttpResponse(400, objectMapper.writeValueAsString(new ErrorModel("Invalid endpoint")));
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
            // TODO: handle exception
            e.printStackTrace();
        }

        return httpResponse;
    }

    private HttpResponse handleUpdateJob(String body) throws Exception {
        HttpResponse httpResponse;

        log.info("Querying for body: " + body);
        if (body == null || body.isEmpty()) {
            httpResponse = new HttpResponse(400, objectMapper.writeValueAsString(new ErrorModel("Body cannot be empty in this request")));
            return httpResponse;
        }
        Job inputJob = objectMapper.readValue(body, Job.class);
        String id = inputJob.getId();
        Job job = JobStore.jobStoreMap.get(id);
        if (id == null || id.isEmpty() || job == null) {
            httpResponse = new HttpResponse(404, objectMapper.writeValueAsString(new ErrorModel("Unknown Job Id")));
            return httpResponse;
        }
        job.setName(inputJob.getName());
        JobStore.jobStoreMap.put(id, job);
        return new HttpResponse(200, "{\"response\":true}");
    }

    private HttpResponse handleDeleteJob(String id) throws Exception {

        HttpResponse httpResponse;
        log.info("Querying for ID: " + id);
        if (id == null || id.isEmpty()) {
            httpResponse = new HttpResponse(400, objectMapper.writeValueAsString(new ErrorModel("Id cannot be empty in this request")));
            return httpResponse;
        }
        Job job = JobStore.jobStoreMap.get(id);
        if (job == null || job.getName() == null) {
            httpResponse = new HttpResponse(404, objectMapper.writeValueAsString(new ErrorModel("Unknown Job Id")));
            return httpResponse;
        }
        JobStore.jobStoreMap.remove(id);
        return new HttpResponse(200, "{\"response\":true}");
    }

    private HttpResponse handleGetJob(String id) throws Exception {

        HttpResponse httpResponse;
        log.info("Querying for ID: " + id);
        if (id == null || id.isEmpty()) {
            httpResponse = new HttpResponse(400, objectMapper.writeValueAsString(new ErrorModel("Id cannot be empty in this request")));
            return httpResponse;
        }
        Job job = JobStore.jobStoreMap.get(id);
        if (job == null || job.getName() == null) {
            httpResponse = new HttpResponse(404, objectMapper.writeValueAsString(new ErrorModel("Unknown Job Id")));
            return httpResponse;
        }
        CreateJobRequest createJobRequest = new CreateJobRequest();
        createJobRequest.setName(job.getName());
        return new HttpResponse(200, objectMapper.writeValueAsString(createJobRequest));
    }

    private HttpResponse handleCreateJob(String body) throws Exception {

        HttpResponse httpResponse;
        CreateJobRequest createJobRequest = objectMapper.readValue(body, CreateJobRequest.class);
        log.info("Running for" + createJobRequest.getName());
        if (createJobRequest.getName() == null || createJobRequest.getName().isEmpty()) {
            httpResponse = new HttpResponse(400, objectMapper.writeValueAsString(new ErrorModel("Name cannot be empty in this request")));
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
