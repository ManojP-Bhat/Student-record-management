package com.srms;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

/**
 * Serves plain files (HTML, CSS, JS) from the "web" folder,
 * the same way a simple static file server would.
 * "/" is treated as a request for index.html.
 */
public class StaticFileHandler implements HttpHandler {

    private final String rootFolder;

    public StaticFileHandler(String rootFolder) {
        this.rootFolder = rootFolder;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestPath = exchange.getRequestURI().getPath();

        if (requestPath.equals("/")) {
            requestPath = "/index.html";
        }

        File file = new File(rootFolder + requestPath);

        if (!file.exists() || file.isDirectory()) {
            byte[] notFound = "404 - File Not Found".getBytes();
            exchange.sendResponseHeaders(404, notFound.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(notFound);
            }
            return;
        }

        exchange.getResponseHeaders().set("Content-Type", contentTypeFor(requestPath));
        byte[] bytes = Files.readAllBytes(file.toPath());
        exchange.sendResponseHeaders(200, bytes.length);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }

    private String contentTypeFor(String path) {
        if (path.endsWith(".html")) return "text/html";
        if (path.endsWith(".css")) return "text/css";
        if (path.endsWith(".js")) return "application/javascript";
        return "text/plain";
    }
}
