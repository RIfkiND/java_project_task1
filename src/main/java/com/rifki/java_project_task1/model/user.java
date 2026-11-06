package com.rifki.java_project_task1.model;

public class User {
    private String nim;
    private String name;

    public User(String nim, String name) {
        this.nim = nim;
        this.name = name;
    }

    public String getNim() {
        return nim;
    }

    public String getname() {
        return name;
    }
}
