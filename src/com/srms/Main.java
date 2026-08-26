package com.srms;

import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;

/**
 * Entry point of the application.
 * Starts a plain Java HTTP server (no external framework needed)
 * that serves the frontend files and the student REST API.
 */
public class Main {

    public static void main(String[] args) throws Exception {
        int port = 8080;

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        // Serves index.html, style.css, app.js from the "web" folder
        server.createContext("/", new StaticFileHandler("web"));

        // Handles GET / POST / DELETE for student records
        server.createContext("/api/students", new StudentApiHandler());

        server.setExecutor(null); // use the default executor
        server.start();

        System.out.println("Server started successfully.");
        System.out.println("Open your browser at: http://localhost:" + port);
    }
}
