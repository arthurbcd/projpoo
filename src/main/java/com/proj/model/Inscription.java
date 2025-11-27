package com.proj.model;

import java.time.LocalDate;

public class Inscription {
    private final String id = java.util.UUID.randomUUID().toString();
    private final Student student;
    private final Session session;
    private final LocalDate inscriptionDate = LocalDate.now();
    private boolean completed = false;

    public Inscription(Student student, Session session) {
        this.student = student;
        this.session = session;
    }

    public Student getStudent() {
        return student;
    }

    public Session getSession() {
        return session;
    }

    public LocalDate getInscriptionDate() {
        return inscriptionDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getId() {
        return id;
    }
}
