package com.proj.model;

import java.util.ArrayList;
import java.util.List;

import com.proj.observer.Observer;

public class Teacher implements Observer {
    private String id;
    private String name;
    private String email;
    private final List<Category> specialties;

    public Teacher(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.specialties = new ArrayList<>();
    }

    public void addSpecialty(Category category) {
        if (!specialties.contains(category)) {
            specialties.add(category);
        }
    }

    @Override
    public void update(String message) {
        System.out.println("Notification for teacher " + name + ": " + message);
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public List<Category> getSpecialties() {
        return specialties;
    }
}
