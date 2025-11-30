package com.proj.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.proj.model.Category;
import com.proj.model.Course;

public class CourseManager {
    private static final CourseManager INSTANCE = new CourseManager();
    private final Map<String, Course> courses = new HashMap<>(); // key by title (simplified)

    private CourseManager() {
    }

    public static CourseManager getInstance() {
        return INSTANCE;
    }

    public Course create(String title, int durationHours, Category category) {
        Course c = new Course(title, durationHours, category);
        courses.put(title, c);
        return c;
    }

    public Course get(String title) {
        return courses.get(title);
    }

    public void updateTitle(String oldTitle, String newTitle) {
        Course c = courses.remove(oldTitle);
        if (c != null) {
            Course replacement = new Course(newTitle, c.getDurationHours(), c.getCategory());
            for (var s : c.getSessions()) {
                replacement.addSession(s);
            }
            courses.put(newTitle, replacement);
        }
    }

    public void remove(String title) {
        courses.remove(title);
    }

    public List<Course> list() {
        return new ArrayList<>(courses.values());
    }
}
