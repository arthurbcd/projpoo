package com.proj.model;

import java.util.ArrayList;
import java.util.List;

public class Course {

    private String title;
    private int durationHours;
    private Category category;
    private final List<Session> sessions;
    private final List<Course> prerequisites;

    public Course(String title, int durationHours, Category category) {
        this.title = title;
        this.durationHours = durationHours;
        this.category = category;
        this.sessions = new ArrayList<>();
        this.prerequisites = new ArrayList<>();
    }

    public void addSession(Session session) {
        this.sessions.add(session);
    }

    public void addPrerequisite(Course course) {
        this.prerequisites.add(course);
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDurationHours() {
        return durationHours;
    }

    public void setDurationHours(int durationHours) {
        this.durationHours = durationHours;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public List<Course> getPrerequisites() {
        return prerequisites;
    }

    @Override
    public String toString() {
        return "Course{" +
                "title='" + title + '\'' +
                ", duration=" + durationHours + "h" +
                ", category=" + category +
                '}';
    }
}
