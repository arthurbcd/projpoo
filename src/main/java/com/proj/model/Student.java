package com.proj.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.proj.observer.Observer;

public class Student implements Observer {
    final private String id;
    private String name;
    private String email;
    private final HashMap<String, Inscription> inscriptions = new HashMap<>();

    public Student(String name, String email) {
        this.id = java.util.UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
    }

    public void addInscription(Inscription inscription) {
        this.inscriptions.put(inscription.getSession().getId(), inscription);
    }

    public void removeInscription(String sessionId) {
        this.inscriptions.remove(sessionId);
    }

    @Override
    public void update(String message) {
        System.out.println("Notification for student " + name + ": " + message);
    }

    public boolean hasCompleted(Course course) {
        for (Inscription inscription : inscriptions.values()) {
            if (inscription.getSession().getCourse().equals(course) && inscription.isCompleted()) {
                return true;
            }
        }
        return false;
    }

    // Getters and Setters
    public String getId() {
        return id;
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

    public List<Inscription> getInscriptions() {
        return new ArrayList<>(inscriptions.values());
    }
}
