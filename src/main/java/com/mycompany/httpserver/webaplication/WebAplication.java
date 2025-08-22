package com.mycompany.httpserver.webaplication;

import static com.mycompany.httpserver.HttpServer.get;
import static com.mycompany.httpserver.HttpServer.startServer;
import static com.mycompany.httpserver.HttpServer.staticfiles;
import java.io.IOException;
import java.net.URISyntaxException;


public class WebAplication {
     public static void main(String[] args) throws IOException, URISyntaxException {
        staticfiles("/webroot/public");
        get("/hello", (req, resp) -> "Hello " + req.getValue("name"));
        get("/pi", (req, resp) -> {
            return String.valueOf(Math.PI); 
        });
        
        startServer(args);
    }
     
     
    
}
