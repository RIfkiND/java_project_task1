package com.rifki.java_project_task1.model;

public class User {
    private String nim;
    private String name;
    private String createdAt;
    private String updatedAt;

    public User(String nim, String name, String createdAt, String updatedAt) {
        this.nim = nim;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    public String getNim() {
        return nim;
    }

    public String getName() {
        return name;
    }
}
