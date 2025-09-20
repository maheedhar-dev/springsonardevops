package com.sonar.devops.model;

public class StudentDTO {

    private Long id;
    private String name;
    private String course;
    private String city;

    public StudentDTO() {
    }

    public StudentDTO(Long id, String name, String course, String city) {
        this.id = id;
        this.name = name;
        this.course = course;
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
