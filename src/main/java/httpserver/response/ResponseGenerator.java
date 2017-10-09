package httpserver.response;

public class ResponseGenerator {

    public String getResponseString(int statusCode, String responseBody) {

        StringBuilder sb = new StringBuilder();
        int len = responseBody.length();
        HTTPStatus status = HTTPStatus.findByStatus(statusCode);
        sb.append("HTTP/1.1 " + status.statusString + "\r\n");
        String cl = "Content-Length: " + len + "\r\n";
        sb.append(cl);
        sb.append("Content-Type: application/json \r\n");
        sb.append("\n");
        sb.append(responseBody);
        return sb.toString();
    }

}
