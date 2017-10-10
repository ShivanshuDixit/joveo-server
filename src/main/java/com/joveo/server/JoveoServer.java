package com.joveo.server;

/**
 * Created by shivanshu.dixit on 24/09/17.
 */

import com.joveo.server.httpserver.handlers.RequestHandler;
import com.joveo.server.httpserver.parser.HttpRequestParser;
import com.joveo.server.httpserver.request.HttpRequest;
import com.joveo.server.httpserver.response.ResponseGenerator;

import org.apache.log4j.BasicConfigurator;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JoveoServer {

    //Default port
    private int portNumber = 9333;

    public static void main(String[] args) throws Exception {

        BasicConfigurator.configure();

        JoveoServer server = new JoveoServer();
        if (args != null && args.length > 0) {
            server.portNumber = new Integer(args[0]);
            log.info("Starting server on port :" + server.portNumber);
        }
        server.runServer();
    }

    /**
     * Starts a server which listens on a port
     */
    private void runServer() throws Exception {

        HttpRequestParser httpRequestParser = new HttpRequestParser();
        ResponseGenerator responseGenerator = new ResponseGenerator();
        RequestHandler requestHandler = new RequestHandler(responseGenerator);

        try (ServerSocket listener = new ServerSocket(portNumber)) {
            //Server running indefinitely and taking incoming requests
            while (true) {
                try (Socket socket = listener.accept()) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    HttpRequest request = httpRequestParser.generateHttpRequest(in);
                     String responseBody = requestHandler.handle(request);
                    DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
                    dout.write(responseBody.getBytes());
                    dout.flush();
                    dout.close();

                } catch (Exception e) {
                    log.error("Exception occurred", e);
                }
            }
        }
    }
}
