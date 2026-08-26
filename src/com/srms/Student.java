package com.srms;

/**
 * Represents a single student record.
 * This is a plain model class (POJO) with no logic other than
 * holding data and converting itself to JSON for the API.
 */
public class Student {

    private int id;
    private String name;
    private String email;
    private String course;
    private int year;

    public Student() {
    }

    public Student(int id, String name, String email, String course, int year) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.course = course;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Converts this student into a small JSON object.
     * Written by hand on purpose, so the project has zero
     * external dependencies besides the MySQL driver.
     */
    public String toJson() {
        return "{"
                + "\"id\":" + id + ","
                + "\"name\":\"" + escape(name) + "\","
                + "\"email\":\"" + escape(email) + "\","
                + "\"course\":\"" + escape(course) + "\","
                + "\"year\":" + year
                + "}";
    }

    private String escape(String value) {
        return value == null ? "" : value.replace("\"", "\\\"");
    }
}
