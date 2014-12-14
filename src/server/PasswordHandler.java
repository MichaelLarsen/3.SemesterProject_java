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
 * @author Michael
 */
public class PasswordHandler implements HttpHandler{
    private final Facade facade;
    private final Gson gson;

    public PasswordHandler() {
        facade = Facade.getFacade(true);
        gson = new Gson();
    }

    @Override
    public void handle(HttpExchange he) throws IOException {
    String response = "";
        int status = 200;

        String method = he.getRequestMethod().toUpperCase();
        System.out.println("method: " + method);

        switch (method) {
                case "POST":
                    System.out.println("POST");
                try {
                    InputStreamReader isr = new InputStreamReader(he.getRequestBody(), "utf-8");
                    BufferedReader br = new BufferedReader(isr);
                    String jsonQuery = br.readLine();
                    System.out.println("password-jsonQuery: " + jsonQuery);
                    String profileStr = facade.changePassword(jsonQuery);
                    response = profileStr;
                }
                catch (IllegalArgumentException iae) {
                    status = 400;
                    response = iae.getMessage();
                }
                catch (IOException e) {
                    status = 500;
                    response = "Internal Server Problem";
                }
                break;
        }
        he.getResponseHeaders().add("Content-Type", "application/json");
        he.getResponseHeaders().add("Access-Control-Allow-Origin", "*"); //Gør så andre sider kan bruge denne
        he.getResponseHeaders().add("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept"); //Sørger for vi kan bruge request-data
        he.sendResponseHeaders(status, 0);
        try (OutputStream os = he.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
    
}
