/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 *
 * @author Michael, Sebastian, Emil og Andreas
 */
class ProfileHandler implements HttpHandler {

    private final Facade facade;
    private final Gson gson;
    
    public ProfileHandler() {
        facade = Facade.getFacade(true);
        gson = new Gson();
    }

    @Override
    public void handle(HttpExchange he) throws IOException {
      String response = "";
        int status = 200;


        String method = he.getRequestMethod().toUpperCase();

        switch (method) {
            case "GET":
                try {
                    response = facade.getProfilesAsJSON();
                }
                catch (NumberFormatException nfe) {
                    response = "Id is not a number";
                    status = 404;
                }
                catch (Exception e) {
                    response = e.getMessage();
                    status = 404;
                }
                break;
        }
        he.getResponseHeaders().add("Content-Type", "application/json");
        he.sendResponseHeaders(status, 0);
        try (OutputStream os = he.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
    
}
