package com.proj.manager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.proj.model.Course;
import com.proj.model.Session;
import com.proj.model.Teacher;

public class SessionManager {
    private static final SessionManager INSTANCE = new SessionManager();
    private final Map<String, Session> sessions = new HashMap<>();

    private SessionManager() {
    }

    public static SessionManager getInstance() {
        return INSTANCE;
    }

    public Session create(Course course, LocalDate startAt, LocalDate endAt, int maxPlaces, Teacher teacher) {
        Session s = new Session(course, startAt, endAt, maxPlaces, teacher);
        sessions.put(s.getId(), s);
        course.addSession(s);
        return s;
    }

    public Session get(String id) {
        return sessions.get(id);
    }

    public List<Session> list() {
        return new ArrayList<>(sessions.values());
    }

    public void remove(String id) {
        Session s = sessions.remove(id);
    }
}
