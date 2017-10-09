package httpserver.response;

public enum HTTPStatus {

  SC_200("200 OK", 200), SC_500("500 Internal Server Error", 500),SC_400("400 Bad Request", 400), SC_404("404 Unknown", 404);
  int status;
  String statusString;
  
  HTTPStatus(String statusString, int status) {
    this.statusString = statusString;
    this.status = status;
  }
  
  public static  HTTPStatus findByStatus( int status) {
    for (HTTPStatus httpStatus : HTTPStatus.values()) {
      if(httpStatus.status == status) {
        return httpStatus;
      }
    }
    return null;
  }
  
}
