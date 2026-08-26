package com.srms;

import com.srms.util.FormParser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Handles every request that comes in on /api/students.
 * GET    /api/students        -> list all students (JSON)
 * POST   /api/students        -> add a new student (form data)
 * DELETE /api/students?id=5   -> delete a student by id
 */
public class StudentApiHandler implements HttpHandler {

    private final StudentDAO dao = new StudentDAO();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();

        try {
            switch (method) {
                case "GET":
                    handleGet(exchange);
                    break;
                case "POST":
                    handlePost(exchange);
                    break;
                case "DELETE":
                    handleDelete(exchange);
                    break;
                default:
                    sendResponse(exchange, 405, "Method Not Allowed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            sendResponse(exchange, 500, "Database error: " + e.getMessage());
        }
    }

    private void handleGet(HttpExchange exchange) throws IOException, SQLException {
        List<Student> students = dao.getAllStudents();

        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < students.size(); i++) {
            json.append(students.get(i).toJson());
            if (i < students.size() - 1) {
                json.append(",");
            }
        }
        json.append("]");

        exchange.getResponseHeaders().set("Content-Type", "application/json");
        sendResponse(exchange, 200, json.toString());
    }

    private void handlePost(HttpExchange exchange) throws IOException, SQLException {
        String body = FormParser.readBody(exchange.getRequestBody());
        Map<String, String> data = FormParser.parse(body);

        Student s = new Student();
        s.setName(data.getOrDefault("name", ""));
        s.setEmail(data.getOrDefault("email", ""));
        s.setCourse(data.getOrDefault("course", ""));
        s.setYear(parseIntSafe(data.get("year")));

        boolean success = dao.addStudent(s);
        sendResponse(exchange, success ? 201 : 400, success ? "Student added" : "Could not add student");
    }

    private void handleDelete(HttpExchange exchange) throws IOException, SQLException {
        String query = exchange.getRequestURI().getQuery();
        Map<String, String> params = FormParser.parse(query);
        int id = parseIntSafe(params.get("id"));

        boolean success = dao.deleteStudent(id);
        sendResponse(exchange, success ? 200 : 404, success ? "Student deleted" : "Student not found");
    }

    private int parseIntSafe(String value) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return 0;
        }
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String body) throws IOException {
        byte[] bytes = body.getBytes();
        exchange.sendResponseHeaders(statusCode, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }
}
