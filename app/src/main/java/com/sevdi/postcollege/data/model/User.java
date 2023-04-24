package com.sevdi.postcollege.data.model;

import java.net.URL;

public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String university;
    private String faculty;
    private Integer startYear;
    private Integer endYear;
    private URL image;

    public User(Long id, String firstName, String lastName, String username, String password, String university, String faculty, Integer startYear, Integer endYear, URL image) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.university = university;
        this.faculty = faculty;
        this.startYear = startYear;
        this.endYear = endYear;
        this.image = image;
    }

    public User(String firstName, String lastName, String username, String password, String university, String faculty, Integer startYear, Integer endYear, URL image) {
        this(null, firstName, lastName, username, password, university, faculty, startYear, endYear, image);
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getUniversity() {
        return university;
    }

    public String getFaculty() {
        return faculty;
    }

    public Integer getStartYear() {
        return startYear;
    }

    public Integer getEndYear() {
        return endYear;
    }

    public URL getImage() {
        return image;
    }
}
