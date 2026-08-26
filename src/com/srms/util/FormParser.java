package com.srms.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Small helper that turns a string like "name=John&course=MCA"
 * into a Map. Used to read both the POST body (the add-student form)
 * and the URL query string (the delete-student id).
 *
 * Written by hand instead of using a library, so a beginner can read
 * every line of what this project depends on.
 */
public class FormParser {

    public static Map<String, String> parse(String rawData) {
        Map<String, String> result = new HashMap<>();
        if (rawData == null || rawData.isEmpty()) {
            return result;
        }

        for (String pair : rawData.split("&")) {
            String[] parts = pair.split("=", 2);
            String key = decode(parts[0]);
            String value = parts.length > 1 ? decode(parts[1]) : "";
            result.put(key, value);
        }
        return result;
    }

    public static String readBody(InputStream is) throws IOException {
        return new String(is.readAllBytes(), StandardCharsets.UTF_8);
    }

    private static String decode(String value) {
        return URLDecoder.decode(value, StandardCharsets.UTF_8);
    }
}
